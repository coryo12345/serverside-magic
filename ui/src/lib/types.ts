export type SpellDefinition = {
  id: string;
  displayName: string;
  description: string;
  flatXpCost: number;
  levelPercentCost: number;
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
  advancementResourceLocation: string;
  advancementName: string;
  unlockDescription: string;
  tabTitle?: string | null;
};

export type SkillTree = {
  skill: Skill;
  unlocked: boolean;
  branches: SkillTree[];
};

export type SpellConfigField = {
  key: string;
  type: 'text' | 'number' | 'boolean' | 'select';
  defaultValue?: string | number | boolean;
  min?: number;
  max?: number;
  options?: string[];
};

export type SecretSkill = {
  id: string;
  name: string;
  description: string;
};

export type SpellConfigResponse = {
  schema: SpellConfigField[] | null;
  config: Record<string, unknown> | null;
};
