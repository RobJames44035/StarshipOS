/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8214113
 * @summary Verify the switch expression's type does not have a constant attached,
 *          and so the switch expression is not elided.
 * @compile SwitchExpressionIsNotAConstant.java
 * @run main SwitchExpressionIsNotAConstant
 */
public class SwitchExpressionIsNotAConstant {

    public static void main(String[] args) {
        int i = 0;
        {
            i = 0;
            int dummy = 1 + switch (i) {
                default -> {
                    i++;
                    yield 1;
                }
            };
            if (i != 1) {
                throw new IllegalStateException("Side effects missing.");
            }
        }
        {
            i = 0;
            int dummy = 1 + switch (i) {
                case -1 -> 1;
                default -> {
                    i++;
                    yield 1;
                }
            };
            if (i != 1) {
                throw new IllegalStateException("Side effects missing.");
            }
        }
        {
            i = 0;
            int dummy = 1 + switch (i) {
                 default :
                    i++;
                    yield 1;
            };
            if (i != 1) {
                throw new IllegalStateException("Side effects missing.");
            }
        }
        {
            i = 0;
            int dummy = 1 + switch (i) {
                case -1: yield 1;
                default:
                    i++;
                    yield 1;
            };
            if (i != 1) {
                throw new IllegalStateException("Side effects missing.");
            }
        }
    }

}
