import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "node:path";
import tailwindcss from "@tailwindcss/vite";
import Components from "unplugin-vue-components/vite";
import { PrimeVueResolver } from "@primevue/auto-import-resolver";

// https://vite.dev/config/
export default defineConfig(({ mode }) => {
  return {
    plugins: [
      vue(), 
      tailwindcss(),
      Components({
        resolvers: [
          PrimeVueResolver()
        ]
      })
    ],
    build: {
      outDir:
        mode == "production"
          ? path.resolve("../src/main/resources/web/")
          : path.resolve("../run/config/servermagic/web"),
      emptyOutDir: true,
    },
  };
});
