package servermagic;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionResult;
import servermagic.data.items.CustomItem;
import servermagic.data.items.ItemInteractionDispatcher;
import servermagic.db.Database;
import servermagic.db.MigrationFailedException;
import servermagic.spells.utils.PlayerSpellFocusCaster;
import servermagic.web.WebPortal;
import servermagic.web.spell.Spells;

public class ServerMagic implements ModInitializer {
	public static final String MOD_ID = "servermagic";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Starting server magic!");

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

		// TODO if needed
		// AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {

		// initialize spell defs for web
		Spells.Get();

		// Handle web portal
		WebApp webApp = new WebApp(null);
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			try {
				Database database = new Database("config/servermagic/data.db");
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
}