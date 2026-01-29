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
import servermagic.web.WebPortal;

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

		// Handle web portal
		try {
			Database database = new Database("config/servermagic/data.db");
			WebPortal webPortal = new WebPortal(database);
			webPortal.start();

			ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
				webPortal.stop();
			});
		} catch (IOException e) {
			LOGGER.error("FAILED TO OPEN DATABASE - WEB PORTAL WILL NOT BE AVAILABLE");
		}
	}
}