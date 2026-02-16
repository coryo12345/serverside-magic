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

export type Skill = {
  id: string;
  name: string;
  description: string;
  parentId: string;
};

export type SkillTree = {
  skill: Skill;
  unlocked: boolean;
  branches: SkillTree[];
};

export type SkillUnlock = {
  id: number;
  username: string;
  skill: string; // Skill.id
};
