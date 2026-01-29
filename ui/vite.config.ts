import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "node:path";

// https://vite.dev/config/
export default defineConfig(({ mode }) => {
  return {
    plugins: [vue()],
    build: {
      outDir:
        mode == "production"
          ? path.resolve("../src/main/resources/web/")
          : path.resolve("../run/config/servermagic/web"),
      emptyOutDir: true,
    },
  };
});
