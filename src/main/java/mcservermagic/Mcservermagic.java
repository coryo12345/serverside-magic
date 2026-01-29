package mcservermagic;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mcservermagic.data.items.CustomItem;
import mcservermagic.data.items.ItemInteractionDispatcher;
import mcservermagic.db.Database;
import mcservermagic.web.WebPortal;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionResult;

public class Mcservermagic implements ModInitializer {
	public static final String MOD_ID = "mc-server-magic";

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
		Database database = new Database("./config/yourmod/data.db");
		WebPortal webPortal = new WebPortal(database);
		webPortal.start();

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			webPortal.stop();
		});
	}
}