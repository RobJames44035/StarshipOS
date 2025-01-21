/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package vm.compiler.coverage.parentheses.share;

import java.util.List;

/**
 * Tiny stack InstructionsExecutor. This executor verifies HotspotInstructionsExecutor
 */
public class TinyInstructionsExecutor implements InstructionsExecutor {

    private int[] stack;
    private int stackHead = -1;

    public TinyInstructionsExecutor(int stackSize) {
        stack = new int[stackSize];
    }

    private void putOnStack(int v) {
        stack[++stackHead] = v;
    }

    private void exec(Instruction instruction) {
        switch (instruction) {
            case ICONST_M1:
                putOnStack(-1);
                break;
            case ICONST_0:
                putOnStack(0);
                break;
            case ICONST_1:
                putOnStack(1);
                break;
            case ICONST_2:
                putOnStack(2);
                break;
            case ICONST_3:
                putOnStack(3);
                break;
            case ICONST_4:
                putOnStack(4);
                break;
            case ICONST_5:
                putOnStack(5);
                break;
            case DUP:
                stack[stackHead + 1] = stack[stackHead];
                stackHead++;
                break;

            case IADD:
                stack[stackHead - 1] += stack[stackHead];
                stackHead--;
                break;
            case ISUB:
                stack[stackHead - 1] -= stack[stackHead];
                stackHead--;
                break;
            case IMUL:
                stack[stackHead - 1] *= stack[stackHead];
                stackHead--;
                break;
            case IOR:
                stack[stackHead - 1] |= stack[stackHead];
                stackHead--;
                break;
            case IAND:
                stack[stackHead - 1] &= stack[stackHead];
                stackHead--;
                break;
            case IXOR:
                stack[stackHead - 1] ^= stack[stackHead];
                stackHead--;
                break;
            case ISHL:
                stack[stackHead - 1] <<= stack[stackHead];
                stackHead--;
                break;
            case ISHR:
                stack[stackHead - 1] >>= stack[stackHead];
                stackHead--;
                break;

            case SWAP: {
                int t = stack[stackHead];
                stack[stackHead] = stack[stackHead - 1];
                stack[stackHead - 1] = t;
                break;
            }
            case NOP:
                break;
            case INEG:
                stack[stackHead] = -stack[stackHead];
                break;
        }
    }

    private int top() {
        return stack[stackHead];
    }

    @Override
    public int execute(List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            exec(instruction);
        }
        return top();
    }
}
