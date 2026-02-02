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
