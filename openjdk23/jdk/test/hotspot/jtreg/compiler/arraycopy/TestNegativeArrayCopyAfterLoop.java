/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @bug 8267904
 * @requires vm.compiler2.enabled
 * @summary C2 inline array_copy move CastIINode(Array Length) before allocation cause crash.
 * @run main/othervm compiler.arraycopy.TestNegativeArrayCopyAfterLoop
 */

package compiler.arraycopy;
import java.util.Arrays;

class test {
    public static int exp_count = 0;
    public int in1 = -4096;
    test (){
        try {
            short sha4[] = new short[1012];
            for (int i = 0; i < sha4.length; i++) {
              sha4[i] = 9;
            }
            Arrays.copyOf(sha4, in1);
        } catch (Exception ex) {
            exp_count++;
        }
    }
}

public class TestNegativeArrayCopyAfterLoop {
    public static void main(String[] args) {
        for (int i = 0; i < 20000; i++) {
            new test();
        }
        if (test.exp_count == 20000) {
            System.out.println("TEST PASSED");
        }
    }
}
