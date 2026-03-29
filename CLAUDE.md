# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What This Is

A Minecraft Fabric mod (1.21.11, Java 21) that adds a magic system — spells, skill tree, mana — with a web-based player portal built in Vue 3.

## Build & Run

```bash
# Build the mod JAR
./gradlew build

# Run the development Minecraft server (web portal at localhost:8080)
./gradlew runServer
```

### Frontend (ui/)

```bash
cd ui
npm install

# Dev build → run/config/servermagic/web (hot-reload during runServer)
npm run build:dev

# Production build → src/main/resources/web (bundled into JAR)
npm run build

# Standalone Vite dev server
npm run dev
```

## Architecture

### Backend (Java/Fabric)

- **`ServerMagic.java`** — mod entry point; registers Fabric events, initializes mana tracking, starts web portal and database on server start.
- **`web/WebPortal.java`** — Javalin HTTP server on port 8080. Serves static web files (from filesystem in dev, from JAR in prod). Three route groups: `AuthRoutes`, `SpellRoutes`, `SkillRoutes`.
- **`web/skill/Skills.java`** — all skill definitions as static constants. Skills can unlock via Minecraft advancements or item interactions.
- **`web/spell/Spells.java`** — spell registry mapping spell IDs to implementations.
- **`db/Database.java`** — SQLite singleton at `config/servermagic/data.db`. Auto-discovers and runs migrations on startup via reflection.
- **`mana/ManaTracker.java`** — per-player mana state, regenerates every 100 ticks, displayed via scoreboard.

### Spell System

All spells extend `BaseSpell`. Key methods to override:
- `spellImplementation()` — spell logic
- `cost()` — mana cost (default 1)
- `id()` — spell identifier string
- `getRequiredSkill()` — skill needed to cast

Spells are cast from `spells/utils/PlayerSpellFocusCaster.java` via item interactions (`data/items/SpellbookItem.java`).

### Database Migrations

Extend `BaseMigration` and place in `db/migrations/`. They are auto-discovered via reflection — no registration needed.

### Authentication Flow

1. Player POSTs username → server sends one-time code in-game chat
2. Player POSTs the code → server issues JWT
3. JWT used as Bearer token for all subsequent API calls

### Entity Binding

Summoned entities/items are "bound" to a player via `EntityBindingManager`. Bound items can't be dropped or moved to other inventories (enforced via mixins).

### Mixins

8 mixins in `mixin/` hook into: item swing, item drop, advancements, player death, item break, dismount, and inventory slot placement (two variants for shulker boxes).

### Frontend (Vue 3 + TypeScript)

- **`lib/api.ts`** — all HTTP calls to the backend
- **`lib/authtoken.ts`** — JWT storage and retrieval
- **`composables/useAuth.ts`** — reactive auth state
- **`components/skilltree/`** — skill tree visualization
- **`components/spellbook/`** — spell slot configuration UI
