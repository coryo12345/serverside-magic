<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue";

const sections = [
  { id: "getting-started", label: "Unlocking Magic", icon: "pi-unlock" },
  { id: "skill-tree", label: "Skill Tree vs. Spells", icon: "pi-sitemap" },
  { id: "spellbook", label: "Spellbook & Casting", icon: "pi-book" },
  { id: "mana", label: "Mana & Experience", icon: "pi-bolt" },
  { id: "progression", label: "Progression & Secrets", icon: "pi-lock" },
];

const tocOpen = ref(false);
const activeSection = ref("getting-started");

function scrollTo(id: string) {
  document
    .getElementById(id)
    ?.scrollIntoView({ behavior: "smooth", block: "start" });
  activeSection.value = id;
  tocOpen.value = false;
}

let observer: IntersectionObserver | null = null;
onMounted(() => {
  observer = new IntersectionObserver(
    (entries) => {
      for (const entry of entries) {
        if (entry.isIntersecting) {
          activeSection.value = entry.target.id;
          break;
        }
      }
    },
    { rootMargin: "-20% 0px -60% 0px", threshold: 0 },
  );
  sections.forEach((s) => {
    const el = document.getElementById(s.id);
    if (el) observer!.observe(el);
  });
});
onUnmounted(() => observer?.disconnect());

const disciplines = [
  {
    name: "Elemental",
    description:
      "Offensive spells like Firebolts, Lightning Strikes, and Ice Shards.",
    icon: "pi-bolt",
    colorClass:
      "from-red-500/20 to-orange-500/10 border-red-500/30 dark:border-red-400/30",
    iconColor: "text-red-500 dark:text-red-400",
    badge: "bg-red-500/10 text-red-700 dark:text-red-300 border-red-400/30",
  },
  {
    name: "Utility",
    description:
      "Conjure bound tools (pickaxes, swords) or summon a rideable mount.",
    icon: "pi-wrench",
    colorClass:
      "from-blue-500/20 to-sky-500/10 border-blue-500/30 dark:border-blue-400/30",
    iconColor: "text-blue-500 dark:text-blue-400",
    badge: "bg-blue-500/10 text-blue-700 dark:text-blue-300 border-blue-400/30",
  },
  {
    name: "Effects",
    description:
      "Self and group buffs like Speed, Invisibility, and Regeneration.",
    icon: "pi-sparkles",
    colorClass:
      "from-green-500/20 to-emerald-500/10 border-green-500/30 dark:border-green-400/30",
    iconColor: "text-green-500 dark:text-green-400",
    badge:
      "bg-green-500/10 text-green-700 dark:text-green-300 border-green-400/30",
  },
  {
    name: "Building",
    description:
      "Spells that construct structures — lines, rings, and full squares.",
    icon: "pi-home",
    colorClass:
      "from-yellow-500/20 to-amber-500/10 border-yellow-500/30 dark:border-yellow-400/30",
    iconColor: "text-yellow-500 dark:text-yellow-400",
    badge:
      "bg-yellow-500/10 text-yellow-700 dark:text-yellow-300 border-yellow-400/30",
  },
];

const combos: { sequence: string[]; label: string }[] = [
  { sequence: ["R", "R", "R"], label: "Triple Right-Click" },
  { sequence: ["R", "L", "R"], label: "Right → Left → Right" },
  { sequence: ["S", "R", "R", "R"], label: "Shift + Triple Right" },
];

const chipClass: Record<string, string> = {
  R: "bg-orange-100 dark:bg-orange-900/30 border-orange-400 text-orange-700 dark:text-orange-300",
  L: "bg-blue-100 dark:bg-blue-900/30 border-blue-400 text-blue-700 dark:text-blue-300",
  S: "bg-purple-100 dark:bg-purple-900/30 border-purple-400 text-purple-700 dark:text-purple-300",
};

const potionRecipes = [
  {
    label: "Recipe A",
    ingredients: ["Amethyst Shard", "Lapis Lazuli", "2× Glass Bottles"],
    result: "2 Mana Potions",
  },
  {
    label: "Recipe B",
    ingredients: ["Gold Ingot", "Glowstone Dust", "2× Glass Bottles"],
    result: "2 Mana Potions",
  },
];
</script>

<template>
  <div class="flex flex-col">
    <!-- Hero Header -->
    <header class="mb-8">
      <div class="flex items-center gap-4 mb-4">
        <div
          class="w-12 h-12 rounded-xl bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center shrink-0 shadow-sm"
        >
          <i
            class="pi pi-book text-2xl text-primary-600 dark:text-primary-400"
          ></i>
        </div>
        <div>
          <h1
            class="text-2xl md:text-3xl font-bold text-surface-900 dark:text-surface-0"
          >
            ServerMagic Wiki
          </h1>
          <p class="text-surface-500 dark:text-surface-400 text-sm mt-0.5">
            Your complete guide to spells, skill trees, and the arcane arts.
          </p>
        </div>
      </div>
      <div
        class="h-px bg-gradient-to-r from-primary-400 via-primary-300 to-transparent dark:from-primary-600 dark:via-primary-700"
      ></div>
    </header>

    <!-- Mobile TOC (collapsible) -->
    <div
      class="lg:hidden mb-6 bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-xl shadow-sm overflow-hidden"
    >
      <button
        @click="tocOpen = !tocOpen"
        class="w-full flex items-center justify-between px-4 py-3 text-sm font-semibold text-surface-700 dark:text-surface-200 cursor-pointer"
      >
        <span class="flex items-center gap-2">
          <i class="pi pi-list text-primary-500 text-xs"></i>
          On This Page
        </span>
        <i
          :class="[
            'pi',
            tocOpen ? 'pi-chevron-up' : 'pi-chevron-down',
            'text-surface-400 text-xs',
          ]"
        ></i>
      </button>
      <div
        v-show="tocOpen"
        class="border-t border-surface-200 dark:border-surface-700 px-4 py-3 space-y-1"
      >
        <button
          v-for="s in sections"
          :key="s.id"
          @click="scrollTo(s.id)"
          class="flex items-center gap-2 w-full text-left text-sm py-1.5 px-2 rounded-lg transition-colors cursor-pointer"
          :class="
            activeSection === s.id
              ? 'text-primary-600 dark:text-primary-400 bg-primary-50 dark:bg-primary-900/20 font-medium'
              : 'text-surface-600 dark:text-surface-400 hover:text-surface-900 dark:hover:text-surface-100'
          "
        >
          <i :class="['pi', s.icon, 'text-xs']"></i>
          {{ s.label }}
        </button>
      </div>
    </div>

    <!-- Two-column body -->
    <div class="flex flex-col lg:flex-row gap-8">
      <!-- Sticky Desktop TOC -->
      <aside class="hidden lg:block w-52 shrink-0">
        <div
          class="sticky top-0 bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-xl shadow-sm p-4"
        >
          <p
            class="text-xs font-bold uppercase tracking-widest text-surface-400 dark:text-surface-500 mb-3 px-2"
          >
            On This Page
          </p>
          <nav class="space-y-0.5">
            <button
              v-for="s in sections"
              :key="s.id"
              @click="scrollTo(s.id)"
              class="flex items-center gap-2 w-full text-left text-sm py-2 px-2 rounded-lg transition-colors duration-150 cursor-pointer"
              :class="
                activeSection === s.id
                  ? 'text-primary-600 dark:text-primary-400 bg-primary-50 dark:bg-primary-900/20 font-semibold'
                  : 'text-surface-500 dark:text-surface-400 hover:text-surface-800 dark:hover:text-surface-200 hover:bg-surface-100 dark:hover:bg-surface-700'
              "
            >
              <span
                class="w-1 h-4 rounded-full transition-colors shrink-0"
                :class="
                  activeSection === s.id ? 'bg-primary-500' : 'bg-transparent'
                "
              ></span>
              <i :class="['pi', s.icon, 'text-xs']"></i>
              {{ s.label }}
            </button>
          </nav>
        </div>
      </aside>

      <!-- Scrollable Content -->
      <div class="flex-1 min-w-0 space-y-10 pb-16">
        <!-- §1 Getting Started: Unlocking Magic -->
        <section id="getting-started">
          <div
            class="bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-xl shadow-sm p-6"
          >
            <!-- Section header -->
            <div class="flex items-center gap-3 mb-2">
              <div
                class="w-9 h-9 rounded-lg bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center shrink-0"
              >
                <i
                  class="pi pi-unlock text-primary-600 dark:text-primary-400"
                ></i>
              </div>
              <h2
                class="text-xl font-bold text-surface-900 dark:text-surface-0"
              >
                Unlocking Magic
              </h2>
            </div>
            <div
              class="h-0.5 bg-gradient-to-r from-primary-400/50 to-transparent mb-5"
            ></div>

            <p
              class="text-surface-600 dark:text-surface-300 text-sm leading-relaxed mb-5"
            >
              Magic is not available from the start. To begin your journey into
              the arcane, you must
              <strong class="text-surface-900 dark:text-surface-0"
                >reach the End</strong
              >.
            </p>

            <!-- The End callout -->
            <div
              class="flex gap-3 bg-amber-50 dark:bg-amber-900/20 border border-amber-300 dark:border-amber-600/40 rounded-xl p-4 mb-6"
            >
              <div class="shrink-0 mt-0.5">
                <i class="pi pi-shield text-amber-500 text-lg"></i>
              </div>
              <div>
                <p
                  class="text-xs font-bold uppercase tracking-widest text-amber-600 dark:text-amber-400 mb-1"
                >
                  Initial Unlock
                </p>
                <p class="text-sm text-amber-800 dark:text-amber-200">
                  Complete the <strong>"The End"</strong> advancement by
                  entering the End dimension. This unlocks all four base magic
                  trees.
                </p>
              </div>
            </div>

            <!-- Four Disciplines -->
            <p
              class="text-xs font-bold uppercase tracking-widest text-surface-400 dark:text-surface-500 mb-3"
            >
              The Four Disciplines
            </p>
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div
                v-for="d in disciplines"
                :key="d.name"
                class="relative rounded-xl border p-5 bg-gradient-to-br overflow-hidden transition-all duration-200 hover:scale-[1.02] hover:shadow-lg"
                :class="d.colorClass"
              >
                <div class="flex items-center gap-3 mb-3">
                  <span
                    class="inline-flex items-center justify-center w-8 h-8 rounded-lg bg-white/20 dark:bg-black/20 shrink-0"
                  >
                    <i :class="['pi', d.icon, 'text-base', d.iconColor]"></i>
                  </span>
                  <span
                    class="font-bold text-surface-900 dark:text-surface-0 text-base"
                    >{{ d.name }}</span
                  >
                </div>
                <p
                  class="text-sm text-surface-600 dark:text-surface-300 leading-relaxed"
                >
                  {{ d.description }}
                </p>
              </div>
            </div>
          </div>
        </section>

        <!-- §2 Skill Tree vs. Spells -->
        <section id="skill-tree">
          <div
            class="bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-xl shadow-sm p-6"
          >
            <div class="flex items-center gap-3 mb-2">
              <div
                class="w-9 h-9 rounded-lg bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center shrink-0"
              >
                <i
                  class="pi pi-sitemap text-primary-600 dark:text-primary-400"
                ></i>
              </div>
              <h2
                class="text-xl font-bold text-surface-900 dark:text-surface-0"
              >
                Skill Tree vs. Spells
              </h2>
            </div>
            <div
              class="h-0.5 bg-gradient-to-r from-primary-400/50 to-transparent mb-5"
            ></div>

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div
                class="bg-surface-50 dark:bg-surface-900 rounded-xl p-5 border border-surface-200 dark:border-surface-700"
              >
                <div class="flex items-center gap-2 mb-3">
                  <div
                    class="w-7 h-7 rounded-md bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center shrink-0"
                  >
                    <i class="pi pi-sitemap text-primary-500 text-xs"></i>
                  </div>
                  <span
                    class="font-semibold text-surface-800 dark:text-surface-100"
                    >Skills</span
                  >
                  <span
                    class="ml-auto text-xs px-2 py-0.5 rounded-full bg-primary-100 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400 font-medium"
                    >Permanent</span
                  >
                </div>
                <p
                  class="text-sm text-surface-600 dark:text-surface-300 leading-relaxed"
                >
                  Permanent unlocks from the skill tree. Most grant a specific
                  <strong class="text-surface-800 dark:text-surface-100"
                    >Spell</strong
                  >
                  you can assign to a combo slot. Some provide
                  <strong class="text-surface-800 dark:text-surface-100"
                    >Passive Enhancements</strong
                  >
                  (e.g., a faster summoned horse).
                </p>
              </div>

              <div
                class="bg-surface-50 dark:bg-surface-900 rounded-xl p-5 border border-surface-200 dark:border-surface-700"
              >
                <div class="flex items-center gap-2 mb-3">
                  <div
                    class="w-7 h-7 rounded-md bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center shrink-0"
                  >
                    <i class="pi pi-book text-primary-500 text-xs"></i>
                  </div>
                  <span
                    class="font-semibold text-surface-800 dark:text-surface-100"
                    >Spell Slots</span
                  >
                  <span
                    class="ml-auto text-xs px-2 py-0.5 rounded-full bg-primary-100 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400 font-medium"
                    >Assignable</span
                  >
                </div>
                <p
                  class="text-sm text-surface-600 dark:text-surface-300 leading-relaxed"
                >
                  Assign your unlocked spells to specific click combos in the
                  <strong class="text-surface-800 dark:text-surface-100"
                    >My Spellbook</strong
                  >
                  tab. Customize your loadout to match your playstyle.
                </p>
              </div>
            </div>
          </div>
        </section>

        <!-- §3 The Spellbook & Casting -->
        <section id="spellbook">
          <div
            class="bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-xl shadow-sm p-6"
          >
            <div class="flex items-center gap-3 mb-2">
              <div
                class="w-9 h-9 rounded-lg bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center shrink-0"
              >
                <i
                  class="pi pi-book text-primary-600 dark:text-primary-400"
                ></i>
              </div>
              <h2
                class="text-xl font-bold text-surface-900 dark:text-surface-0"
              >
                The Spellbook & Casting
              </h2>
            </div>
            <div
              class="h-0.5 bg-gradient-to-r from-primary-400/50 to-transparent mb-5"
            ></div>

            <!-- Crafting callout -->
            <div
              class="flex items-center gap-3 bg-surface-50 dark:bg-surface-900 border border-surface-200 dark:border-surface-700 rounded-xl p-4 mb-6"
            >
              <i class="pi pi-wrench text-primary-500 shrink-0"></i>
              <div>
                <p
                  class="text-xs font-bold uppercase tracking-widest text-surface-400 mb-0.5"
                >
                  Crafting
                </p>
                <p class="text-sm text-surface-700 dark:text-surface-200">
                  Place a <strong>Book</strong> in any crafting grid (Shapeless)
                  to craft your Spellbook. Without it, you cannot cast spells.
                </p>
              </div>
            </div>

            <!-- 3-step combo system -->
            <p
              class="text-xs font-bold uppercase tracking-widest text-surface-400 dark:text-surface-500 mb-3"
            >
              The Combo System
            </p>
            <ol class="space-y-3 mb-6">
              <li class="flex items-start gap-3">
                <span
                  class="w-6 h-6 rounded-full bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center text-xs font-bold text-primary-600 dark:text-primary-400 shrink-0 mt-0.5"
                  >1</span
                >
                <div>
                  <p
                    class="text-sm font-semibold text-surface-800 dark:text-surface-100"
                  >
                    Initiate
                  </p>
                  <p class="text-sm text-surface-600 dark:text-surface-300">
                    Every spell begins with a <strong>Right-Click</strong> or
                    <strong>Shift + Right-Click</strong> while holding your
                    Spellbook.
                  </p>
                </div>
              </li>
              <li class="flex items-start gap-3">
                <span
                  class="w-6 h-6 rounded-full bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center text-xs font-bold text-primary-600 dark:text-primary-400 shrink-0 mt-0.5"
                  >2</span
                >
                <div>
                  <p
                    class="text-sm font-semibold text-surface-800 dark:text-surface-100"
                  >
                    Sequence
                  </p>
                  <p class="text-sm text-surface-600 dark:text-surface-300">
                    Follow up with two more clicks — Left or Right — to complete
                    your combo.
                  </p>
                </div>
              </li>
              <li class="flex items-start gap-3">
                <span
                  class="w-6 h-6 rounded-full bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center text-xs font-bold text-primary-600 dark:text-primary-400 shrink-0 mt-0.5"
                  >3</span
                >
                <div>
                  <p
                    class="text-sm font-semibold text-surface-800 dark:text-surface-100"
                  >
                    Cast!
                  </p>
                  <p class="text-sm text-surface-600 dark:text-surface-300">
                    Once the 3-click combo is complete, the spell fires. Your
                    current progress is shown on the
                    <strong>Action Bar</strong>.
                  </p>
                </div>
              </li>
            </ol>

            <!-- Combo chip examples -->
            <p
              class="text-xs font-bold uppercase tracking-widest text-surface-400 dark:text-surface-500 mb-4"
            >
              Example Combos
            </p>
            <div class="space-y-4">
              <div
                v-for="combo in combos"
                :key="combo.label"
                class="flex items-center gap-4 flex-wrap"
              >
                <div class="flex items-center gap-1 shrink-0">
                  <template v-for="(token, i) in combo.sequence" :key="i">
                    <span
                      class="inline-flex items-center justify-center w-9 h-9 rounded-lg text-sm font-bold border-2 shadow-sm select-none"
                      :class="chipClass[token]"
                      >{{ token }}</span
                    >
                    <i
                      v-if="i < combo.sequence.length - 1"
                      class="pi pi-angle-right text-surface-400 text-xs"
                    ></i>
                  </template>
                </div>
                <span
                  class="text-sm text-surface-500 dark:text-surface-400 italic"
                  >{{ combo.label }}</span
                >
              </div>
            </div>

            <!-- Legend -->
            <div
              class="flex gap-5 mt-5 flex-wrap border-t border-surface-200 dark:border-surface-700 pt-4"
            >
              <span
                class="flex items-center gap-2 text-xs text-surface-500 dark:text-surface-400"
              >
                <span
                  class="w-5 h-5 rounded bg-orange-100 dark:bg-orange-900/30 border-2 border-orange-400 inline-block"
                ></span>
                R = Right-Click
              </span>
              <span
                class="flex items-center gap-2 text-xs text-surface-500 dark:text-surface-400"
              >
                <span
                  class="w-5 h-5 rounded bg-blue-100 dark:bg-blue-900/30 border-2 border-blue-400 inline-block"
                ></span>
                L = Left-Click
              </span>
              <span
                class="flex items-center gap-2 text-xs text-surface-500 dark:text-surface-400"
              >
                <span
                  class="w-5 h-5 rounded bg-purple-100 dark:bg-purple-900/30 border-2 border-purple-400 inline-block"
                ></span>
                S = Shift + Right-Click
              </span>
            </div>
          </div>
        </section>

        <!-- §4 Mana & Experience -->
        <section id="mana">
          <div
            class="bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-xl shadow-sm p-6"
          >
            <div class="flex items-center gap-3 mb-2">
              <div
                class="w-9 h-9 rounded-lg bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center shrink-0"
              >
                <i
                  class="pi pi-bolt text-primary-600 dark:text-primary-400"
                ></i>
              </div>
              <h2
                class="text-xl font-bold text-surface-900 dark:text-surface-0"
              >
                Mana & Experience
              </h2>
            </div>
            <div
              class="h-0.5 bg-gradient-to-r from-primary-400/50 to-transparent mb-5"
            ></div>

            <p
              class="text-surface-600 dark:text-surface-300 text-sm leading-relaxed mb-6"
            >
              In ServerMagic, your
              <strong class="text-surface-900 dark:text-surface-0"
                >XP is your Mana</strong
              >. Every spell consumes a portion of your experience — some at a
              flat cost, others as a percentage of your current level's
              requirement.
            </p>

            <!-- Potion recipes -->
            <p
              class="text-xs font-bold uppercase tracking-widest text-surface-400 dark:text-surface-500 mb-3"
            >
              Mana Potion Recipes
            </p>
            <div class="grid grid-cols-1 sm:grid-cols-2 gap-4 mb-5">
              <div
                v-for="recipe in potionRecipes"
                :key="recipe.label"
                class="bg-surface-50 dark:bg-surface-900 rounded-xl p-4 border border-surface-200 dark:border-surface-700"
              >
                <p
                  class="text-xs font-bold uppercase tracking-widest text-surface-400 mb-3"
                >
                  {{ recipe.label }}
                </p>
                <div class="flex items-center gap-1.5 flex-wrap mb-3">
                  <template v-for="(ing, i) in recipe.ingredients" :key="i">
                    <span
                      class="inline-flex items-center text-xs bg-surface-200 dark:bg-surface-700 text-surface-700 dark:text-surface-300 px-2 py-1 rounded-md font-medium"
                    >
                      {{ ing }}
                    </span>
                    <span
                      v-if="i < recipe.ingredients.length - 1"
                      class="text-surface-400 text-xs font-bold"
                      >+</span
                    >
                  </template>
                </div>
                <div class="flex items-center gap-2">
                  <i class="pi pi-arrow-right text-primary-500 text-xs"></i>
                  <span
                    class="text-sm font-semibold text-primary-600 dark:text-primary-400"
                    >{{ recipe.result }}</span
                  >
                </div>
              </div>
            </div>

            <!-- Pro Tip -->
            <div
              class="flex gap-3 bg-amber-50 dark:bg-amber-900/20 border border-amber-300 dark:border-amber-600/40 rounded-xl p-4"
            >
              <div class="shrink-0 mt-0.5">
                <i class="pi pi-lightbulb text-amber-500 text-lg"></i>
              </div>
              <div>
                <p
                  class="text-xs font-bold uppercase tracking-widest text-amber-600 dark:text-amber-400 mb-1"
                >
                  Pro Tip
                </p>
                <p class="text-sm text-amber-800 dark:text-amber-200">
                  Mana Potions provide a fixed XP amount, so they're
                  <strong>most effective below Level 10</strong>. Use them early
                  and often to maintain spellcasting momentum.
                </p>
              </div>
            </div>
          </div>
        </section>

        <!-- §5 Progression & Secrets -->
        <section id="progression">
          <div
            class="bg-surface-0 dark:bg-surface-800 border border-surface-200 dark:border-surface-700 rounded-xl shadow-sm p-6"
          >
            <div class="flex items-center gap-3 mb-2">
              <div
                class="w-9 h-9 rounded-lg bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center shrink-0"
              >
                <i
                  class="pi pi-lock text-primary-600 dark:text-primary-400"
                ></i>
              </div>
              <h2
                class="text-xl font-bold text-surface-900 dark:text-surface-0"
              >
                Progression & Secrets
              </h2>
            </div>
            <div
              class="h-0.5 bg-gradient-to-r from-primary-400/50 to-transparent mb-5"
            ></div>

            <p
              class="text-surface-600 dark:text-surface-300 text-sm leading-relaxed mb-5"
            >
              While many skills are unlocked via standard Minecraft advancements
              (e.g., getting a Blaze Rod unlocks Firebolt), some require
              <strong class="text-surface-900 dark:text-surface-0"
                >specific in-world actions</strong
              >. The skill tree will always hint at what's needed.
            </p>

            <div class="space-y-3">
              <!-- Skill Tree row -->
              <div
                class="flex items-start gap-3 bg-surface-50 dark:bg-surface-900 border border-surface-200 dark:border-surface-700 rounded-xl p-4"
              >
                <div
                  class="w-8 h-8 rounded-lg bg-primary-100 dark:bg-primary-900/30 flex items-center justify-center shrink-0 mt-0.5"
                >
                  <i class="pi pi-sitemap text-primary-500 text-sm"></i>
                </div>
                <div>
                  <p
                    class="text-sm font-semibold text-surface-800 dark:text-surface-100 mb-1"
                  >
                    Skill Tree
                  </p>
                  <p
                    class="text-sm text-surface-600 dark:text-surface-300 leading-relaxed"
                  >
                    Browse the <strong>Skills</strong> tab to see every
                    unlockable ability. Each node shows exactly what you need to
                    unlock it — or at least a hint.
                  </p>
                </div>
              </div>

              <!-- Secrets row (purple glow) -->
              <div
                class="flex items-start gap-3 bg-surface-900 dark:bg-surface-950 border border-purple-600/40 rounded-xl p-4"
                style="box-shadow: 0 0 12px 1px rgba(139, 92, 246, 0.12)"
              >
                <div
                  class="w-8 h-8 rounded-lg bg-purple-900/60 border border-purple-500/40 flex items-center justify-center shrink-0 mt-0.5"
                >
                  <i class="pi pi-eye text-purple-300 text-sm"></i>
                </div>
                <div>
                  <p class="text-sm font-semibold text-purple-200 mb-1">
                    Secrets
                  </p>
                  <p class="text-sm text-surface-300 leading-relaxed">
                    Some skills are entirely hidden from the regular tree. They
                    won't appear until you've discovered them through specific
                    actions — then they surface in the
                    <strong class="text-purple-300">Secrets</strong> tab.
                    Explore everything.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>
