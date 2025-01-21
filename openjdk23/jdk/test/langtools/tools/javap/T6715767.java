/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6715767
 * @summary javap on java.lang.ClassLoader crashes
 * @modules jdk.jdeps/com.sun.tools.javap
 */

import java.io.*;

public class T6715767 {
    public static void main(String... args) throws Exception {
        new T6715767().run();
    }

    void run() throws Exception {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String[] args = { "java.lang.ClassLoader" };
        int rc = com.sun.tools.javap.Main.run(args, pw);
        if (rc != 0 ||
            sw.toString().indexOf("at com.sun.tools.javap.JavapTask.run") != -1) {
            System.err.println("rc: " + rc);
            System.err.println("log:\n" + sw);
            throw new Exception("unexpected result");
        }
    }
}

