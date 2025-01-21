/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug     8010310
 * @summary Error processing sources with -private
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 */

import java.io.File;

public class Test {
    public static void main(String... args) throws Exception {
        File testSrc = new File(System.getProperty("test.src"));
        String[] jdoc_args = {
            "-d", "out",
            new File(testSrc, Test.class.getSimpleName() + ".java").getPath()
        };
        int rc = jdk.javadoc.internal.tool.Main.execute(jdoc_args);
        if (rc != 0)
            throw new Exception("unexpected return code from javadoc: " + rc);
    }

    static int array[] = { 1, 2, 3};
    static int method(int p) { return p; }
    static int value = 0;

    public int not_static_not_final = 1;
    public static int static_not_final = 2;
    public final int not_static_final = 3;
    public static final int static_final = 4;

    public static final int array_index = array[0];
    public static final int method_call = method(0);
    public static final int inner_class = new Test() { }.method(0);
    public static final int new_class = new Test().method(0);
    public static final int pre_inc = ++value;
    public static final int pre_dec = --value;
    public static final int post_inc = value++;
    public static final int post_dec = value--;
}

