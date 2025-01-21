/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.jvmci.compilerToVM;

import jdk.test.lib.Utils;
import jdk.test.whitebox.WhiteBox;

import java.util.Random;

class DummyClass extends DummyAbstractClass {
    private static final WhiteBox WB = WhiteBox.getWhiteBox();
    int p1 = 5;
    int p2 = 6;

    public int dummyInstanceFunction() {
        String str1 = "123123123";
        double x = 3.14;
        int y = Integer.parseInt(str1);

        return y / (int) x;
    }

    public int dummyEmptyInstanceFunction() {
        return 42;
    }

    public static int dummyEmptyStaticFunction() {
        return -42;
    }

    @Override
    public int dummyAbstractFunction() {
        int z = p1 * p2;
        return (int) (Math.cos(p2 - p1 + z) * 100);
    }

    @Override
    public void dummyFunction() {
        dummyEmptyInstanceFunction();
    }

    public void withLoop() {
        long tier4 = (Long) WB.getVMFlag("Tier4BackEdgeThreshold");
        for (long i = 0; i < tier4; ++i) {
            randomProfile();
        }
    }

    private double randomProfile() {
        String str1 = "123123123";
        double x = 3.14;
        int y = Integer.parseInt(str1);

        Random rnd = Utils.getRandomInstance();
        if (rnd.nextDouble() > 0.2) {
            return y / (int) x;
        } else {
            return x / y;
        }
    }

}
