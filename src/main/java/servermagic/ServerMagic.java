package servermagic;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import servermagic.data.items.CustomItem;
import servermagic.data.items.utils.ItemInteractionDispatcher;
import servermagic.db.Database;
import servermagic.db.MigrationFailedException;
import servermagic.mana.ManaInfo;
import servermagic.mana.ManaScoreboard;
import servermagic.mana.ManaTracker;
import servermagic.spells.SummonMount;
import servermagic.spells.utils.PlayerSpellFocusCaster;
import servermagic.web.WebPortal;
import servermagic.web.spell.Spells;

public class ServerMagic implements ModInitializer {
	public static final String MOD_ID = "servermagic";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private int tickCount = 0;
	private ManaTracker manaTracker;

	@Override
	public void onInitialize() {
		LOGGER.info("Starting server magic!");
		this.manaTracker = new ManaTracker();

		UseItemCallback.EVENT.register((player, world, hand) -> {
			ItemInteractionDispatcher dispatcher = new ItemInteractionDispatcher(world, player, hand);
			Optional<CustomItem> item = dispatcher.getHeldCustomItem();
			if (item.isEmpty()) {
				return InteractionResult.PASS;
			} else {
				return dispatcher.dispatchUse();
			}
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ItemInteractionDispatcher dispatcher = new ItemInteractionDispatcher(world, player, hand);
			Optional<CustomItem> item = dispatcher.getHeldCustomItem();
			if (item.isEmpty()) {
				return InteractionResult.PASS;
			} else {
				return dispatcher.dispatchAttack(entity, hitResult);
			}
		});
		// if we need it?
		// AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {

		ServerTickEvents.END_WORLD_TICK.register((ServerLevel world) -> {
			tickCount = (++tickCount % 1000); // so we dont get too large
			// once every third second - things that don't need to happen often
			if (tickCount % 60 == 0) {
				SummonMount.tickCleanup(world);
			}
			// if we do this too frequently we overload the client with messages
			if (tickCount % 100 == 0) {
				this.handleManaRegenUpdates(world);
			}
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ManaInfo manaInfo = manaTracker.initPlayer(handler.player.getPlainTextName());
			ManaScoreboard.onPlayerJoin(handler.player);
			ManaScoreboard.updateManaDisplay(manaInfo, handler.player);
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			// Note: sending packets on disconnect is a no-op since the connection
			// is closing, but calling it is harmless and keeps the code symmetric.
			// The client cleans itself up on disconnect anyway.
			ManaScoreboard.onPlayerQuit(handler.player);
			this.manaTracker.removePlayer(handler.player.getPlainTextName());
		});

		// initialize spell defs for web
		Spells.Get();

		// Handle web portal
		WebApp webApp = new WebApp(null);
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			try {
				Database database = new Database("config/servermagic/data.db");
				// needed for mixins :(
				Database.SetDB(database);
				// initialize spell caster singleton
				PlayerSpellFocusCaster.Init(database);
				WebPortal webPortal = new WebPortal(database, server);
				webPortal.start();
				webApp.webPortal = webPortal;

			} catch (IOException e) {
				LOGGER.error("FAILED TO OPEN DATABASE - WEB PORTAL WILL NOT BE AVAILABLE");
			} catch (MigrationFailedException e) {
				LOGGER.error("FAILED TO RUN MIGRATIONS - MOD WILL NOT WORK");
			}
		});

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			if (webApp.webPortal != null) {
				webApp.webPortal.stop();
			}
		});
	}

	private class WebApp {
		public WebPortal webPortal;

		public WebApp(WebPortal webPortal) {
			this.webPortal = webPortal;
		}
	}

	private void handleManaRegenUpdates(ServerLevel world) {
		// update the record of current mana for all players
		this.manaTracker.manaRegenTick(1.0);
		// then send the info to the clients
		for (ServerPlayer player : world.players()) {
			Optional<ManaInfo> m = manaTracker.getManaInfoForPlayer(player.getPlainTextName());
			if (m.isEmpty()) {
				continue;
			}
			ManaScoreboard.updateManaDisplay(m.get(), player);
		}
	}
}