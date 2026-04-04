<script setup lang="ts">
import { ref, watch } from "vue";
import { type SpellDefinition, type SpellConfigField } from "../../lib/types";
import { api } from "../../lib/api";
import { useToast } from "primevue/usetoast";
import Dialog from "primevue/dialog";
import Button from "primevue/button";
import Select from "primevue/select";
import InputText from "primevue/inputtext";
import InputNumber from "primevue/inputnumber";
import ToggleSwitch from "primevue/toggleswitch";

const props = defineProps<{
  spell: SpellDefinition | null;
}>();

const visible = defineModel<boolean>("visible");
const toast = useToast();

const schema = ref<SpellConfigField[] | null>(null);
const configValues = ref<Record<string, unknown>>({});
const isSaving = ref(false);

watch(visible, async (isNowVisible) => {
  if (isNowVisible && props.spell) {
    schema.value = null;
    configValues.value = {};

    const result = await api.getSpellConfig(props.spell.id);
    if (result.isError()) return;

    const data = result.get();
    schema.value = data.schema;

    if (data.schema) {
      const initialValues: Record<string, unknown> = {};
      for (const field of data.schema) {
        if (data.config && data.config[field.key] !== undefined) {
          initialValues[field.key] = data.config[field.key];
        } else if (field.defaultValue !== undefined) {
          initialValues[field.key] = field.defaultValue;
        } else if (field.type === "text") {
          initialValues[field.key] = "";
        } else if (field.type === "number") {
          initialValues[field.key] = field.min ?? 0;
        } else if (field.type === "boolean") {
          initialValues[field.key] = false;
        } else if (field.type === "select") {
          initialValues[field.key] = field.options?.[0] ?? "";
        }
      }
      configValues.value = initialValues;
    }
  }
});

async function saveConfig() {
  if (!props.spell) return;
  isSaving.value = true;
  const result = await api.setSpellConfig(props.spell.id, configValues.value);
  isSaving.value = false;
  if (result.isError()) {
    toast.add({
      severity: "error",
      summary: "Error",
      detail: "Failed to save configuration",
      life: 3000,
    });
  } else {
    toast.add({
      severity: "success",
      summary: "Saved",
      detail: "Configuration saved",
      life: 2000,
    });
  }
}
</script>

<template>
  <Dialog
    v-model:visible="visible"
    modal
    dismissable-mask
    :header="spell?.displayName || 'Spell Details'"
    :style="{ width: '40rem' }"
    :breakpoints="{ '1199px': '75vw', '575px': '90vw' }"
  >
    <div v-if="spell" class="flex flex-col gap-6">
      <!-- Icon and Basic Info -->
      <div class="flex items-center gap-4">
        <div
          class="w-20 h-20 shrink-0 rounded-xl bg-primary-100 dark:bg-primary-900/30 text-primary-600 dark:text-primary-400 flex items-center justify-center border-2 border-primary-200 dark:border-primary-800"
        >
          <i
            :class="`pi ${spell.icon || 'pi-bolt'}`"
            style="font-size: 3rem"
          ></i>
        </div>
        <div class="flex flex-col gap-1">
          <h3 class="text-xl font-bold text-surface-900 dark:text-surface-0">
            {{ spell.displayName }}
          </h3>
          <span
            v-if="spell.group"
            class="text-sm font-medium px-2 py-0.5 rounded-md bg-surface-100 dark:bg-surface-800 text-surface-600 dark:text-surface-400 self-start border border-surface-200 dark:border-surface-700"
          >
            {{ spell.group }}
          </span>
        </div>
      </div>

      <!-- Details -->
      <div class="space-y-4">
        <div>
          <label
            class="text-sm font-semibold text-surface-500 dark:text-surface-400 block mb-1"
          >
            Description
          </label>
          <p class="text-surface-900 dark:text-surface-100 leading-relaxed">
            {{ spell.description }}
          </p>
        </div>

        <div>
          <label
            class="text-sm font-semibold text-surface-500 dark:text-surface-400 block mb-1"
          >
            Mana Cost
          </label>
          <p class="text-surface-900 dark:text-surface-100 font-mono">
            {{ spell.cost }}
          </p>
        </div>
      </div>

      <!-- Configuration -->
      <div
        v-if="schema && schema.length > 0"
        class="space-y-4 border-t border-surface-200 dark:border-surface-700 pt-4"
      >
        <label
          class="text-sm font-semibold text-surface-500 dark:text-surface-400 block"
        >
          Configuration
        </label>

        <div
          v-for="field in schema"
          :key="field.key"
          class="flex flex-col gap-1"
        >
          <label
            class="text-sm text-surface-700 dark:text-surface-300 capitalize"
          >
            {{ field.key }}
          </label>

          <Select
            v-if="field.type === 'select'"
            v-model="configValues[field.key]"
            :options="field.options"
            class="w-full"
          />
          <InputText
            v-else-if="field.type === 'text'"
            v-model="(configValues[field.key] as string)"
            class="w-full"
          />
          <InputNumber
            v-else-if="field.type === 'number'"
            v-model="(configValues[field.key] as number)"
            :min="field.min"
            :max="field.max"
            class="w-full"
          />
          <ToggleSwitch
            v-else-if="field.type === 'boolean'"
            v-model="(configValues[field.key] as boolean)"
          />
        </div>

        <Button
          label="Save Configuration"
          icon="pi pi-save"
          :loading="isSaving"
          class="w-full mt-2"
          @click="saveConfig"
        />
      </div>
    </div>
  </Dialog>
</template>
