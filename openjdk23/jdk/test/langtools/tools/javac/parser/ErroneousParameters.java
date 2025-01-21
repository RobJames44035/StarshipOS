/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class ErroneousParameters {

    public static void test(int... extraVarArg, int additionalParam) { }
    public static void test(byte param...) { }
    public static void test(char param,) { }
    public static void test(short param[) { }
    public static void test(int param=) { }

}
