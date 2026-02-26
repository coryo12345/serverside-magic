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
  advancementName?: string;
  unlockDescription?: string;
}

export interface Connection {
  from: { x: number; y: number };
  to: { x: number; y: number };
  unlocked: boolean;
}

export function useSkillTreeLayout(rootTree: Ref<SkillTree> | SkillTree) {
  const levelDistance = 250;

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
      currentAngle: number,
      availableArc: number,
      parentPos: { x: number; y: number } | null,
    ) => {
      const radius = level * levelDistance;
      const x = radius * Math.cos((currentAngle * Math.PI) / 180);
      const y = radius * Math.sin((currentAngle * Math.PI) / 180);

      const currentPos = { x, y };

      nodes.push({
        id: tree.skill.id,
        name: tree.skill.name,
        description: tree.skill.description,
        unlocked: tree.unlocked,
        x,
        y,
        parentId: tree.skill.parentId,
        advancementName: tree.skill.advancementName,
        unlockDescription: tree.skill.unlockDescription,
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
        
        // For the root, we want to use the full 360 degrees.
        // For sub-branches, we tighten the spread to keep them 'behind' the parent.
        let actualArc;
        if (level === 0) {
          actualArc = 360;
        } else {
          const anglePerLeaf = Math.max(15, 60 / (level + 1));
          const preferredSpread = (totalLeaves - 1) * anglePerLeaf;
          actualArc = Math.min(availableArc, preferredSpread, 120);
        }
        
        let startAngle = currentAngle - actualArc / 2;

        // If there's only one branch and it's not the root, it should continue straight
        if (tree.branches.length === 1 && level > 0) {
          processNode(tree.branches[0]!, level + 1, currentAngle, availableArc, currentPos);
        } else {
          for (const branch of tree.branches) {
            const branchLeaves = countLeaves(branch);
            const branchArc = (actualArc * branchLeaves) / totalLeaves;
            const branchAngle = startAngle + branchArc / 2;

            processNode(
              branch,
              level + 1,
              branchAngle,
              branchArc,
              currentPos,
            );

            startAngle += branchArc;
          }
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
