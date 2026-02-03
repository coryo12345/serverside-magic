export type SpellDefinition = {
  id: string;
  displayName: string;
  description: string;
  cost: string;
  icon?: string;
  group?: string;
};

export type SpellSlot = {
  id: number;
  username: string;
  spell_id: string;
  slot: number;
};

export type PlayerSpellResponse = {
  spellSlotMap: Record<number, SpellDefinition>;
  availableSpells: Record<string, SpellDefinition>;
};
