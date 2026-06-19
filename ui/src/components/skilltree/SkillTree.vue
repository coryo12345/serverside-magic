<template>
  <div
    ref="viewport"
    class="relative w-full h-full min-h-[500px] overflow-hidden rounded-xl cursor-grab active:cursor-grabbing select-none border border-surface-200 dark:border-surface-700 bg-galaxy shadow-2xl"
    @mousedown="startDrag"
    @mousemove="onDrag"
    @mouseup="stopDrag"
    @mouseleave="stopDrag"
    @wheel.prevent="handleWheel"
  >
    <!-- Galaxy Background Layers -->
    <div class="absolute inset-0 pointer-events-none overflow-hidden" style="contain: layout paint">
      <div class="stars-1"></div>
      <div class="stars-2"></div>
      <div class="stars-3"></div>
      <div class="nebula"></div>
    </div>

    <!-- Transformable Canvas -->
    <div
      ref="canvas"
      class="absolute inset-0"
      style="transform-origin: 0 0; will-change: transform"
    >
      <!-- Connections (SVG) -->
      <svg class="absolute inset-0 overflow-visible pointer-events-none">
        <g v-for="(conn, index) in connections" :key="index">
          <!-- Glow Layer (Static geometry instead of expensive filter) -->
          <line
            v-if="conn.unlocked"
            :x1="conn.from.x"
            :y1="conn.from.y"
            :x2="conn.to.x"
            :y2="conn.to.y"
            stroke="#a855f7"
            stroke-width="12"
            opacity="0.2"
            stroke-linecap="round"
          />
          <!-- Core Line -->
          <line
            :x1="conn.from.x"
            :y1="conn.from.y"
            :x2="conn.to.x"
            :y2="conn.to.y"
            :stroke="conn.unlocked ? '#a855f7' : '#334155'"
            :stroke-width="conn.unlocked ? 4 : 2"
            :stroke-dasharray="conn.unlocked ? '0' : '8,4'"
            class="connection-line"
            :class="{ unlocked: conn.unlocked }"
          />
        </g>
      </svg>

      <!-- Nodes -->
      <div
        v-for="node in nodes"
        :key="node.id"
        class="absolute"
        :style="{ left: `${node.x}px`, top: `${node.y}px` }"
        @click.stop="openSkillDetails(node)"
      >
        <div
          class="skill-node w-36 h-36 transform -translate-x-1/2 -translate-y-1/2 p-4 rounded-xl border-2 text-center transition-all duration-300"
          :class="[
            node.unlocked
              ? 'bg-primary-700 border-primary shadow-[0_0_20px_rgba(168,85,247,0.6)] text-white'
              : 'bg-surface-900 border-surface-700 text-surface-500',
          ]"
        >
          <div
            class="absolute -inset-0.5 bg-gradient-to-r from-purple-500 to-blue-500 rounded-xl blur opacity-0 transition duration-300 group-hover:opacity-100"
            v-if="node.unlocked"
          ></div>
          <div
            class="relative h-full w-full flex justify-center items-center text-center"
          >
            <!-- TODO these should be icons once i have them -->
            <div
              class="font-bold text-md mb-1 wrap-break-word text-wrap tracking-wide"
            >
              {{ node.name }}
            </div>
          </div>

          <!-- Locked Overlay Icon -->
          <div v-if="!node.unlocked" class="absolute top-1 right-1">
            <i class="pi pi-lock text-[10px]"></i>
          </div>
        </div>
      </div>
    </div>

    <!-- UI Overlay (Zoom controls) -->
    <div
      class="absolute bottom-6 right-6 flex flex-col gap-3 pointer-events-auto"
    >
      <Button
        icon="pi pi-plus"
        severity="primary"
        rounded
        raised
        @click="zoom(0.1)"
      />
      <Button
        icon="pi pi-minus"
        severity="primary"
        rounded
        raised
        @click="zoom(-0.1)"
      />
      <Button
        icon="pi pi-refresh"
        severity="secondary"
        rounded
        raised
        @click="resetView"
      />
    </div>

    <!-- Instructions Overlay -->
    <div
      class="absolute bottom-6 left-6 text-white/40 text-xs pointer-events-none font-medium bg-black/20 px-3 py-1.5 rounded-full backdrop-blur-sm"
    >
      <i class="pi pi-arrows-alt mr-1"></i> Drag to pan • Scroll to zoom
    </div>

    <SkillDetailDialog v-model:visible="isDetailsOpen" :node="selectedNode" />
  </div>
</template>

<script setup lang="ts">
import Button from "primevue/button";
import { nextTick, onMounted, onUnmounted, ref, toRef } from "vue";
import {
  useSkillTreeLayout,
  type PositionedNode,
} from "../../composables/useSkillTreeLayout";
import type { SkillTree } from "../../lib/types";
import SkillDetailDialog from "./SkillDetailDialog.vue";

const props = defineProps<{
  tree: SkillTree;
}>();

const emit = defineEmits<{
  (e: "reload"): void;
}>();

const { nodes, connections } = useSkillTreeLayout(toRef(props, "tree"));

const viewport = ref<HTMLElement | null>(null);
const canvas = ref<HTMLElement | null>(null);

// Plain variables — bypasses Vue reactivity on every drag/zoom frame
let offsetX = 0;
let offsetY = 0;
let currentScale = 0.8;
let isDragging = false;
const lastMousePos = { x: 0, y: 0 };
let rafId: number | null = null;

const selectedNode = ref<PositionedNode | null>(null);
const isDetailsOpen = ref(false);

const openSkillDetails = (node: PositionedNode) => {
  selectedNode.value = node;
  isDetailsOpen.value = true;
};

const applyTransform = () => {
  if (canvas.value) {
    canvas.value.style.transform = `translate(${offsetX}px, ${offsetY}px) scale(${currentScale})`;
  }
};

let hasInitialized = false;
let resizeObserver: ResizeObserver | null = null;

const centerOnRoot = () => {
  if (viewport.value) {
    offsetX = viewport.value.clientWidth / 2;
    offsetY = viewport.value.clientHeight / 2;
    applyTransform();
  }
};

onMounted(async () => {
  await nextTick();
  applyTransform();
  if (viewport.value) {
    if (viewport.value.clientWidth > 0 && viewport.value.clientHeight > 0) {
      centerOnRoot();
      hasInitialized = true;
    } else {
      resizeObserver = new ResizeObserver(() => {
        if (
          !hasInitialized &&
          viewport.value &&
          viewport.value.clientWidth > 0
        ) {
          centerOnRoot();
          hasInitialized = true;
          resizeObserver?.disconnect();
        }
      });
      resizeObserver.observe(viewport.value);
    }
  }
});

onUnmounted(() => {
  resizeObserver?.disconnect();
});

const startDrag = (e: MouseEvent) => {
  isDragging = true;
  lastMousePos.x = e.clientX;
  lastMousePos.y = e.clientY;
};

const onDrag = (e: MouseEvent) => {
  if (!isDragging) return;
  offsetX += e.clientX - lastMousePos.x;
  offsetY += e.clientY - lastMousePos.y;
  lastMousePos.x = e.clientX;
  lastMousePos.y = e.clientY;
  if (rafId === null) {
    rafId = requestAnimationFrame(() => {
      applyTransform();
      rafId = null;
    });
  }
};

const stopDrag = () => {
  isDragging = false;
};

const handleWheel = (e: WheelEvent) => {
  const delta = e.deltaY > 0 ? -0.1 : 0.1;
  const rect = viewport.value?.getBoundingClientRect();
  if (!rect) return;
  zoom(delta, { x: e.clientX - rect.left, y: e.clientY - rect.top });
};

const zoom = (delta: number, focalPoint?: { x: number; y: number }) => {
  const oldScale = currentScale;
  const newScale = Math.max(0.2, Math.min(2, oldScale + delta));
  if (oldScale === newScale) return;

  const fx = focalPoint?.x ?? (viewport.value?.clientWidth ?? 0) / 2;
  const fy = focalPoint?.y ?? (viewport.value?.clientHeight ?? 0) / 2;

  offsetX = fx - (fx - offsetX) * (newScale / oldScale);
  offsetY = fy - (fy - offsetY) * (newScale / oldScale);
  currentScale = newScale;
  applyTransform();
};

const resetView = () => {
  currentScale = 1;
  centerOnRoot();
};
</script>

<style scoped>
.bg-galaxy {
  background: #020617; /* Slate-950 */
}

.nebula {
  position: absolute;
  inset: -50%;
  will-change: transform;
  background:
    radial-gradient(
      circle at 30% 30%,
      rgba(139, 92, 246, 0.15) 0%,
      transparent 50%
    ),
    radial-gradient(
      circle at 70% 60%,
      rgba(59, 130, 246, 0.15) 0%,
      transparent 50%
    ),
    radial-gradient(
      circle at 50% 40%,
      rgba(236, 72, 153, 0.1) 0%,
      transparent 60%
    );
  /* filter: blur(60px); Removed for performance */
  animation: nebula-float 30s ease-in-out infinite alternate;
}

@keyframes nebula-float {
  from {
    transform: scale(1) translate(0, 0);
  }
  to {
    transform: scale(1.1) translate(2%, 2%);
  }
}

.stars-1,
.stars-2,
.stars-3 {
  position: absolute;
  background-image:
    radial-gradient(1.5px 1.5px at 10% 10%, #fff, transparent),
    radial-gradient(1px 1px at 20% 25%, #fff, transparent),
    radial-gradient(2px 2px at 35% 40%, #a855f7, transparent),
    radial-gradient(1px 1px at 50% 15%, #fff, transparent),
    radial-gradient(1.5px 1.5px at 65% 70%, #60a5fa, transparent),
    radial-gradient(2px 2px at 80% 30%, #fff, transparent),
    radial-gradient(1px 1px at 90% 85%, #fff, transparent),
    radial-gradient(1.5px 1.5px at 15% 75%, #f472b6, transparent),
    radial-gradient(1px 1px at 40% 60%, #fff, transparent),
    radial-gradient(1px 1px at 75% 10%, #fff, transparent);
  background-size: 300px 300px;
}

/* Static layer — no animation, no need to extend beyond viewport */
.stars-1 {
  inset: 0;
}

/* Animated layers drift by 300px — extend just enough to cover the shift */
.stars-2 {
  inset: -300px;
  background-size: 450px 450px;
  opacity: 0.6;
  will-change: transform;
  animation: drift 180s linear infinite;
}

.stars-3 {
  inset: -300px;
  background-size: 600px 600px;
  opacity: 0.4;
  will-change: transform;
  animation: drift 240s linear infinite reverse;
}

@keyframes drift {
  from {
    transform: translate(0, 0);
  }
  to {
    transform: translate(300px, 300px);
  }
}

.skill-node {
  cursor: pointer;
  z-index: 10;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
}

.skill-node:hover {
  /* transform: translate(-50%, -50%) scale(1.08); */
  filter: brightness(1.1);
  border-color: #d8b4fe; /* Purple-300 */
}

.connection-line {
  transition: all 0.5s ease;
}

.connection-line.unlocked {
  stroke-dasharray: 0;
}
</style>
