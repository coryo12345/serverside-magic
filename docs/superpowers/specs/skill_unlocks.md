# Skill Unlock Proposals

This document outlines the proposed unlock methods for all magic skills. All trees are locked until the player achieves **"The End"** (`end/root`). 

## Progression Rules
1. **Ancestry Requirement:** A skill cannot be unlocked until all its parent skills are unlocked.
2. **The End Gate:** The base "Unlock Magic" skill for every tree requires visiting The End.
3. **One-and-Done:** All unlocks are triggered by a single event (Advancement, Item Use, or Crafting).

---

## 🏗️ Building Tree
*Focus: Utility in construction. Unlocks are generally straightforward.*

| Skill | Parent | Unlock Method | Reason |
|-------|--------|---------------|--------|
| **Unlock Magic** | None | Advancement: `end/root` | Base gate. |
| **Build: Line** | Unlock Magic | Advancement: `husbandry/plant_seed` | Basic "placing" concept. |
| **Build: Square** | Unlock Magic | Advancement: `adventure/summon_iron_golem` | Constructing a structure. |
| **Build: Ring** | Unlock Magic | Advancement: `adventure/trade` | Circular economy/interaction. |

---

## 🔥 Elemental Tree
*Focus: Offensive and environmental magic. Unlocks scale with combat and exploration difficulty.*

| Skill | Parent | Unlock Method | Reason |
|-------|--------|---------------|--------|
| **Unlock Magic** | None | Advancement: `end/root` | Base gate. |
| **Firebolt** | Unlock Magic | Advancement: `nether/obtain_blaze_rod` | Harvesting the essence of fire. |
| **Fireball** | Firebolt | Advancement: `nether/return_to_sender` | Mastering explosive projectiles. |
| **Ring of Fire** | Fireball | Advancement: `nether/explore_nether` | Familiarity with hellish landscapes. |
| **Meteor Shower** | Fireball | Advancement: `nether/summon_wither` | Calling down celestial destruction. |
| **Lightning Strike** | Unlock Magic | Advancement: `adventure/lightning_rod_with_villager_no_fire` | Harnessing the storm. |
| **Sunbeam** | Lightning Strike | Advancement: `adventure/totem_of_undying` | Channeling holy/life energy. |
| **Chain Lightning** | Lightning Strike | Advancement: `adventure/two_birds_one_arrow` | Arcing energy between targets. |
| **Arcane Missiles** | Chain Lightning | Advancement: `adventure/arbalistic` | Precision magical projectiles. |
| **Poison Cloud** | Unlock Magic | Advancement: `nether/brew_potion` | Alchemy and toxins. |
| **Bee Swarm** | Poison Cloud | Advancement: `husbandry/safely_harvest_honey` | Command over nature/swarms. |
| **Wind Charge** | Poison Cloud | Advancement: `adventure/who_needs_rockets` | Mastering kinetic air. |
| **Wind Gust** | Wind Charge | Advancement: `adventure/fall_from_world_height` | Surviving the heights of the wind. |
| **Gravity Well** | Wind Charge | Throw an Ender Pearl into the Void while in The End | High risk, high reward. |
| **Freeze** | Unlock Magic | Advancement: `adventure/walk_on_powder_snow_with_leather_boots` | Embracing the cold. |
| **Frost Nova** | Freeze | Kill a Stray with a Fireball | Thermal shock theme. |
| **Earthen Spike** | Unlock Magic | Advancement: `adventure/adventuring_time` | Explore the earth. |
| **Spectral Grasp** | Gravity Well | Kill a Shulker while affected by Levitation | Mastering the effect. |
| **Iron Maiden** | Spectral Grasp | Right-click an Anvil with a Soul Lantern | Fusing soul and iron. |
| **Desecrated Ground** | Ring of Fire | Advancement: `nether/get_wither_skull` | Embracing the dark/withered side. |
| **Reap** | Desecrated Ground | Advancement: `adventure/kill_all_mobs` | The ultimate dealer of death. |

---

## 🛠️ Utility Tree
*Focus: Tools, mounts, and storage. Unlocks tied to high-tier gear and husbandry.*

| Skill | Parent | Unlock Method | Reason |
|-------|--------|---------------|--------|
| **Unlock Magic** | None | Advancement: `end/root` | Base gate. |
| **Summon Mount** | Unlock Magic | Advancement: `husbandry/tame_an_animal` | Bonding with creatures. |
| **Mount: Gallop** | Summon Mount | Advancement: `husbandry/breed_an_animal` | Understanding mount potential. |
| **Mount: High Jump** | Summon Mount | Advancement: `adventure/bullseye` | Precision and power. |
| **Mount: Armor** | Summon Mount | Advancement: `story/shiny_gear` | Protecting your companion. |
| **Bound Spyglass** | Unlock Magic | Advancement: `adventure/spyglass_at_parrot` | Far-reaching vision. |
| **Bag of Holding** | Bound Spyglass | Right-click an Ender Chest while holding a Shulker Box | Ultimate storage combination. |
| **Bound Sword** | Unlock Magic | **Smith Netherite Sword** | Mastery of the blade. |
| **Bound Axe** | Bound Sword | **Smith Netherite Axe** | Mastery of the cleaver. |
| **Bound Spear** | Bound Sword | **Smith Netherite Spear** | Mastering spears. |
| **Bound Pickaxe** | Bound Spyglass | **Smith Netherite Pickaxe** | Mastery of the mine. |
| **Bound Shovel** | Bound Spyglass | **Smith Netherite Shovel** | Mastery of the earth. |
| **Bound Hoe** | Bound Spyglass | **Smith Netherite Hoe** | Serious dedication. |
| **Bound Shears** | Bound Spyglass | Advancement: `adventure/honey_block_slide` | Delicate interaction. |
| **Bound Fishing Rod** | Bound Spyglass | Advancement: `husbandry/fishy_business` | Patience and utility. |
| **Spectral Hammer** | Bound Pickaxe | Advancement: `nether/netherite_armor` | Ultimate destructive power. |

---

## ✨ Effects Tree
*Focus: Passive and active status effects. Unlocks tied to potion usage.*

| Skill | Parent | Unlock Method |
|-------|--------|---------------|
| **Unlock Magic** | None | Advancement: `end/root` |
| **Speed Spell** | Unlock Magic | Advancement: `nether/brew_potion` |
| **Jump Boost** | Speed Spell | Drink Potion of Leaping |
| **Slow Falling** | Jump Boost | Drink Potion of Slow Falling |
| **Night Vision** | Unlock Magic | Drink Potion of Night Vision |
| **Invisibility** | Night Vision | Drink Potion of Invisibility |
| **Invisibility Others** | Invisibility | Use Splash Potion of Invisibility |
| **Water Breathing** | Night Vision | Drink Potion of Water Breathing |
| **Heal Spell** | Unlock Magic | Drink Potion of Healing |
| **Heal Others** | Heal Spell | Use Splash Potion of Healing |
| **Regeneration** | Heal Spell | Drink Potion of Regeneration |
| **Regeneration Others** | Regeneration | Use Splash Potion of Regeneration |
| **Fire Resistance** | Unlock Magic | Drink Potion of Fire Resistance |
| **Fire Resistance Others** | Fire Resistance | Use Splash Potion of Fire Resistance |
| **Strength** | Fire Resistance | Drink Potion of Strength |

---

## 🌑 Secrets
*Focus: Hidden and powerful abilities. Hard to unlock.*

| Skill | Parent | Unlock Method | Reason |
|-------|--------|---------------|--------|
| **Shadow Step** | Secrets | Advancement: `end/respawn_dragon` | Manipulating life and position. |
| **Battlemage Armor** | Secrets | Smith a piece of netherite armor | Armor? Idk. |
| **Angel Wings** | Secrets | Reach Y > 1000 while wearing elytra | Reaching the heavens |
| **Void Rift** | Secrets | Advancement: `end/dragon_breath` | Harnessing the dragon's essence. |
| **Flying Carpet** | Secrets | Hold a carpet while standing on a happy ghast | A literal flying carpet |
