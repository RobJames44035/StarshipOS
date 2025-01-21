/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package vm.compiler.optimizations.partialpeel;

import nsk.share.GoldChecker;
import vm.compiler.share.CompilerTest;
import vm.compiler.share.CompilerTestLauncher;
import vm.compiler.share.Random;

import java.util.Arrays;
import java.util.List;

public class WhileWhile {


    public static void main(String[] args) {
        GoldChecker goldChecker = new GoldChecker("WhileWhile");

        for (CompilerTest test : whilewhileTests) {
            goldChecker.println(test + " = " + CompilerTestLauncher.launch(test));
        }

        goldChecker.check();
    }

    private final static int N = 1000;
    static int x0 = 232;
    static int x1 = 562;
    static int x2 = 526;
    static int x3 = 774;

    public static final List<CompilerTest<Integer>> whilewhileTests = Arrays.asList(

        //invariant condition
        new CompilerTest<Integer>("whilewhile1") {
            @Override
            public Integer execute(Random random) {
                int k = x0 + random.nextInt(1000);
                int i = k + x2;
                int s = x1;
                int j = x2;

                while (i < N + x3) {
                    i++;
                    while (j < N) {
                        j++;
                        s++;
                        if (x2 > x1) {
                            k += j;
                        }

                    }
                }

                return s + k;
            }
        },


        //inner while with condition on outer while counter
        new CompilerTest<Integer>("whilewhile2") {
            @Override
            public Integer execute(Random random) {
                int k = x0 + random.nextInt(1000);
                int i = k + x2;
                int s = x1;
                int j = x2;

                while (i < N + x3) {
                    i++;
                    while (j < N + i) {
                        j++;
                        s++;
                        if (x2 > x1) {
                            k += j;
                        }

                    }
                }
                return s + k;
            }
        },

        //inner while in if branch
        new CompilerTest<Integer>("whilewhile3") {
            @Override
            public Integer execute(Random random) {
                int k = x0 + random.nextInt(1000);
                int i = k + x2;
                int s = x1;
                int j = x2;

                while (i < N + x3) {
                    i++;
                    if (i > x2) {
                        while (j < N + i) {
                            j++;
                            s += k;
                            if (x2 > x1) {
                                k += j;
                            }
                        }
                    }
                }
                return s + k;
            }
        },

        //two inner while
        new CompilerTest<Integer>("whilewhile4") {
            @Override
            public Integer execute(Random random) {
                int k = x0 + random.nextInt(1000);
                int i = k + x2;
                int s = x1;
                int j = x2;

                while (i < N + x3) {
                    i++;
                    s++;
                    if (i > x2) {
                        while (j < N + i) {
                            j++;
                            s++;
                            if (x2 > x1) {
                                k += j;
                            }
                        }
                    }

                    j = x2;
                    while (j < x2 + x3) {
                        j++;
                        if (x2 > x1) {
                            j += x0;
                            s++;
                            k += j;
                        }
                    }
                }
                return s + k;
            }
        }
    );

}
