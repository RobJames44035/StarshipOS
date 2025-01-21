/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/* @test
 * @summary Pass values on stack.
 * @requires os.arch == "riscv64"
 * @run main/native compiler.calls.TestManyArgs
 */

package compiler.calls;

public class TestManyArgs {
    static {
        System.loadLibrary("TestManyArgs");
    }

    native static void scramblestack();

    native static int checkargs(int arg0, short arg1, byte arg2,
                                int arg3, short arg4, byte arg5,
                                int arg6, short arg7, byte arg8,
                                int arg9, short arg10, byte arg11);

    static int compiledbridge(int arg0, short arg1, byte arg2,
                              int arg3, short arg4, byte arg5,
                              int arg6, short arg7, byte arg8,
                              int arg9, short arg10, byte arg11) {
        return checkargs(arg0, arg1, arg2, arg3, arg4, arg5,
                         arg6, arg7, arg8, arg9, arg10, arg11);
    }

    static public void main(String[] args) {
        scramblestack();
        for (int i = 0; i < 20000; i++) {
            int res = compiledbridge((int)0xf, (short)0xf, (byte)0xf,
                                     (int)0xf, (short)0xf, (byte)0xf,
                                     (int)0xf, (short)0xf, (byte)0xf,
                                     (int)0xf, (short)0xf, (byte)0xf);
            if (res != 0) {
                throw new RuntimeException("Test failed");
            }
        }
    }
}
