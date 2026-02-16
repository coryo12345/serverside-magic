import { computed, type Ref } from "vue";
import type { SkillTree } from "../lib/types";

export interface PositionedNode {
  id: string;
  name: string;
  description: string;
  unlocked: boolean;
  x: number;
  y: number;
  parentId: string | null;
}

export interface Connection {
  from: { x: number; y: number };
  to: { x: number; y: number };
  unlocked: boolean;
}

export function useSkillTreeLayout(rootTree: Ref<SkillTree> | SkillTree) {
  // const nodeRadius = 60;
  const levelDistance = 200;

  const countLeaves = (tree: SkillTree): number => {
    if (tree.branches.length === 0) return 1;
    return tree.branches.reduce((acc, branch) => acc + countLeaves(branch), 0);
  };

  const layoutResult = computed(() => {
    const treeValue = "value" in rootTree ? rootTree.value : rootTree;
    const nodes: PositionedNode[] = [];
    const connections: Connection[] = [];

    const processNode = (
      tree: SkillTree,
      level: number,
      startAngle: number,
      endAngle: number,
      parentPos: { x: number; y: number } | null,
    ) => {
      const angle = (startAngle + endAngle) / 2;
      const radius = level * levelDistance;

      const x = radius * Math.cos((angle * Math.PI) / 180);
      const y = radius * Math.sin((angle * Math.PI) / 180);

      const currentPos = { x, y };

      nodes.push({
        id: tree.skill.id,
        name: tree.skill.name,
        description: tree.skill.description,
        unlocked: tree.unlocked,
        x,
        y,
        parentId: tree.skill.parentId,
      });

      if (parentPos) {
        connections.push({
          from: parentPos,
          to: currentPos,
          unlocked: tree.unlocked,
        });
      }

      if (tree.branches.length > 0) {
        const totalLeaves = countLeaves(tree);
        let currentStartAngle = startAngle;

        for (const branch of tree.branches) {
          const branchLeaves = countLeaves(branch);
          const angularWidth =
            ((endAngle - startAngle) * branchLeaves) / totalLeaves;

          processNode(
            branch,
            level + 1,
            currentStartAngle,
            currentStartAngle + angularWidth,
            currentPos,
          );

          currentStartAngle += angularWidth;
        }
      }
    };

    // For the root node, we use 0-360 degrees
    processNode(treeValue, 0, 0, 360, null);

    return { nodes, connections };
  });

  return {
    nodes: computed(() => layoutResult.value.nodes),
    connections: computed(() => layoutResult.value.connections),
  };
}
