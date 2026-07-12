package servermagic;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import servermagic.commands.MagicCommands;
import servermagic.entities.Entities;
import servermagic.cosmetics.CosmeticAppearanceManager;
import servermagic.data.items.LootboxItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import servermagic.data.items.CustomItem;
import servermagic.data.items.utils.ItemInteractionDispatcher;
import servermagic.db.Database;
import servermagic.db.MigrationFailedException;
import servermagic.entitybinding.EntityBindingLifecycleHandler;
import servermagic.entitybinding.EntityBindingManager;
import servermagic.entitybinding.EntityBindingTickHandler;
import servermagic.spells.arcane.ArcaneMissileManager;
import servermagic.spells.ArrowVolley;
import servermagic.spells.Backstep;
import servermagic.spells.Blink;
import servermagic.spells.SetGhostTool;
import servermagic.spells.DesecratedGround;
import servermagic.spells.GravityWell;
import servermagic.spells.IronMaiden;
import servermagic.spells.BeeSwarm;
import servermagic.spells.FloatingShield;
import servermagic.spells.SpectralHammer;
import servermagic.spells.SummonBifrost;
import servermagic.spells.Sunbeam;
import servermagic.spells.MeteorShower;
import servermagic.spells.SpectralGrasp;
import servermagic.spells.RingOfFire;
import servermagic.spells.VoidRift;
import servermagic.spells.freeze.FreezeProjectileManager;
import servermagic.spells.FlyingCarpet;
import servermagic.spells.ShadowClone;
import servermagic.spells.SummonMount;
import servermagic.spells.utils.PlayerSpellFocusCaster;
import servermagic.web.WebPortal;
import servermagic.web.skill.SkillGranter;
import servermagic.web.skill.Skills;
import servermagic.web.spell.Spells;

public class ServerMagic implements ModInitializer {
	public static final String MOD_ID = "servermagic";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private int tickCount = 0;

	@Override
	public void onInitialize() {
		LOGGER.info("Starting server magic!");

		Entities.register();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			MagicCommands.register(dispatcher);
		});

		UseItemCallback.EVENT.register((player, world, hand) -> {
			ItemInteractionDispatcher dispatcher = new ItemInteractionDispatcher(world, player, hand);
			Optional<CustomItem> item = dispatcher.getHeldCustomItem();
			if (!item.isEmpty()) {
				return dispatcher.dispatchUse();
			}

			Optional<Database> db = Database.GetDB();
			if (db.isPresent() && world instanceof ServerLevel && player instanceof ServerPlayer sp) {
				SkillGranter granter = new SkillGranter((ServerLevel) world, sp, db.get());
				ItemStack stack = sp.getItemInHand(hand);
				granter.grantFromItemUse(stack);

				// BLINK unlock: throw an ender pearl during a 20+ block fall
				if (stack.is(net.minecraft.world.item.Items.ENDER_PEARL)) {
					Blink.onEnderPearlThrown(sp);
				}
			}
			return InteractionResult.PASS;
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			// Suppress offhand block interactions when main hand holds a spellbook.
			// Without this, each right-click casting gesture also places a block from the
			// offhand.
			if (hand == InteractionHand.OFF_HAND
					&& world instanceof ServerLevel
					&& player instanceof ServerPlayer sp) {
				ItemInteractionDispatcher mainHandDispatcher = new ItemInteractionDispatcher(world, sp,
						InteractionHand.MAIN_HAND);
				if (mainHandDispatcher.isCustomItem()) {
					return InteractionResult.FAIL;
				}
			}

			Optional<Database> db = Database.GetDB();
			if (db.isPresent() && world instanceof ServerLevel && player instanceof ServerPlayer) {
				SkillGranter granter = new SkillGranter((ServerLevel) world, (ServerPlayer) player, db.get());
				ItemStack heldItem = player.getItemInHand(hand);
				granter.grantFromBlockInteraction(world.getBlockState(hitResult.getBlockPos()), heldItem);
			}
			return InteractionResult.PASS;
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

		ServerTickEvents.END_SERVER_TICK.register(EntityBindingTickHandler::tick);
		ServerTickEvents.END_SERVER_TICK.register(ArrowVolley::tick);
		ServerTickEvents.END_SERVER_TICK.register(FreezeProjectileManager::tick);
		ServerTickEvents.END_SERVER_TICK.register(ArcaneMissileManager::tick);
		ServerTickEvents.END_SERVER_TICK.register(VoidRift::tick);
		ServerTickEvents.END_SERVER_TICK.register(GravityWell::tick);
		ServerTickEvents.END_SERVER_TICK.register(RingOfFire::tick);
		ServerTickEvents.END_SERVER_TICK.register(DesecratedGround::tick);
		ServerTickEvents.END_SERVER_TICK.register(MeteorShower::tick);
		ServerTickEvents.END_SERVER_TICK.register(SpectralGrasp::tick);
		ServerTickEvents.END_SERVER_TICK.register(IronMaiden::tick);
		ServerTickEvents.END_SERVER_TICK.register(BeeSwarm::tick);
		ServerTickEvents.END_SERVER_TICK.register(Sunbeam::tick);
		ServerTickEvents.END_SERVER_TICK.register(SpectralHammer::tick);
		ServerTickEvents.END_SERVER_TICK.register(SummonBifrost::tick);
		ServerTickEvents.END_SERVER_TICK.register(FloatingShield::tick);
		ServerTickEvents.END_SERVER_TICK.register(ShadowClone::tick);

		ServerTickEvents.END_LEVEL_TICK.register((ServerLevel world) -> {
			tickCount = (++tickCount % 1000); // so we dont get too large
			// once every third second - things that don't need to happen often
			if (tickCount % 60 == 0) {
				SummonMount.tickCleanup(world);
				FlyingCarpet.tickCleanup(world);
			}
			if (tickCount % 20 == 0) {
				this.checkSkillUnlockConditions(world);
			}
			Blink.tickClutchCheck(world);
			Backstep.tickBackstepCheck(world);
		});

		ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
			if (entity.entityTags().contains("servermagic:floating_shield")) {
				entity.discard();
				return;
			}
			if (entity.entityTags().contains("shadow-clone-wolf")
					|| entity.entityTags().contains("shadow-clone-mannequin")) {
				entity.discard();
				return;
			}
			if (entity instanceof net.minecraft.world.entity.item.ItemEntity itemEntity
					&& world instanceof ServerLevel sl
					&& sl.dimensionTypeRegistration().is(BuiltinDimensionTypes.END)) {
				SetGhostTool.onItemEntityLoadedInEnd(itemEntity, sl);
			}
		});

		ServerEntityEvents.ENTITY_UNLOAD.register(EntityBindingLifecycleHandler::onEntityUnload);

		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
			if (source.getDirectEntity() instanceof Wolf wolf) {
				ShadowClone.onWolfDealtDamage(wolf);
			}
			return true;
		});

		ServerTickEvents.END_SERVER_TICK.register(CosmeticAppearanceManager::tick);

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			Optional<Database> db = Database.GetDB();
			if (db.isPresent()) {
				CosmeticAppearanceManager.loadPlayerCosmetics(handler.player, db.get());
			}
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			CosmeticAppearanceManager.revertAndClearPlayer(handler.player);
			FloatingShield.onPlayerDisconnect(handler.player);
			ShadowClone.onPlayerDisconnect(handler.player);
		});

		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			Optional<Database> db = Database.GetDB();
			if (db.isPresent()) {
				CosmeticAppearanceManager.loadPlayerCosmetics(newPlayer, db.get());
			}
		});

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (!source.isBuiltin())
				return;
			if (!key.equals(BuiltInLootTables.ANCIENT_CITY) && !key.equals(BuiltInLootTables.END_CITY_TREASURE))
				return;

			LootPool.Builder pool = LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1))
					.add(LootboxItem.buildLootEntry());
			tableBuilder.withPool(pool);
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
			FloatingShield.onServerStopping();
				ShadowClone.onServerStopping();
		});

		ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
			EntityBindingManager.clearCache();
		});
	}

	private class WebApp {
		public WebPortal webPortal;

		public WebApp(WebPortal webPortal) {
			this.webPortal = webPortal;
		}
	}

	private void checkSkillUnlockConditions(ServerLevel world) {
		Optional<Database> db = Database.GetDB();
		if (db.isEmpty()) {
			return;
		}

		// GRAVITY_WELL: ender pearl falling into the void in The End
		if (world.dimensionTypeRegistration().is(BuiltinDimensionTypes.END)) {
			for (net.minecraft.world.entity.Entity e : world.getAllEntities()) {
				if (e instanceof ThrownEnderpearl pearl && pearl.getY() < -10
						&& pearl.getOwner() instanceof ServerPlayer player) {
					SkillGranter.grantSkillForPlayer(db.get(), player, servermagic.web.skill.Skills.GRAVITY_WELL);
				}
			}
		}

		// FLYING_CARPET: player riding a Happy Ghast while holding carpet
		// ANGEL_WINGS: player at elevation while (wearing elytra OR holding feather &
		// gold)
		for (ServerPlayer player : world.players()) {
			if (player.getVehicle() instanceof HappyGhast) {
				ItemStack mainHand = player.getMainHandItem();
				ItemStack offHand = player.getOffhandItem();
				boolean holdingCarpet = mainHand.is(ItemTags.WOOL_CARPETS)
						|| offHand.is(ItemTags.WOOL_CARPETS);
				if (holdingCarpet) {
					SkillGranter.grantSkillForPlayer(db.get(), player, servermagic.web.skill.Skills.FLYING_CARPET);
				}
			}

			if (player.getY() > 501) {
				boolean wearingElytra = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST)
						.is(Items.ELYTRA);
				boolean holdingItems = player.getItemHeldByArm(HumanoidArm.LEFT).is(Items.GOLD_INGOT)
						&& player.getItemHeldByArm(HumanoidArm.RIGHT).is(Items.FEATHER);
				if (wearingElytra || holdingItems) {
					SkillGranter.grantSkillForPlayer(db.get(), player, servermagic.web.skill.Skills.ANGEL_WINGS);
				}
			}

			if (player.getY() < -60 && player.level().dimension() == Level.OVERWORLD
					&& player.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemTags.PICKAXES)) {
				SkillGranter.grantSkillForPlayer(db.get(), player, Skills.SPECTRAL_HAMMER);
			}
		}

		// BIFROST: stand above Y=250 in Overworld during a thunderstorm holding an amethyst shard
		if (world.dimension() == Level.OVERWORLD && world.isThundering()) {
			for (ServerPlayer player : world.players()) {
				if (player.getY() > 250) {
					ItemStack main = player.getMainHandItem();
					ItemStack off = player.getOffhandItem();
					if (main.is(Items.AMETHYST_SHARD) || off.is(Items.AMETHYST_SHARD)) {
						SkillGranter.grantSkillForPlayer(db.get(), player, Skills.BIFROST);
					}
				}
			}
		}

		// SHADOW_CLONE: a fox or snow fox near a player is holding a sword
		for (net.minecraft.world.entity.Entity e : world.getAllEntities()) {
			if (e instanceof net.minecraft.world.entity.animal.fox.Fox fox
					&& fox.getMainHandItem().is(net.minecraft.tags.ItemTags.SWORDS)) {
				net.minecraft.world.entity.player.Player nearest = world.getNearestPlayer(fox, 20.0);
				if (nearest instanceof ServerPlayer sp) {
					SkillGranter.grantSkillForPlayer(db.get(), sp, Skills.SHADOW_CLONE);
				}
			}
		}

		// GHOST_TOOL: toss an item into the End void, follow it yourself, survive
		SetGhostTool.tickVoidCheck(world);
	}
}