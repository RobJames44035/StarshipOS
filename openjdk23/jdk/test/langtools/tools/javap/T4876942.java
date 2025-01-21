/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 4876942 6715251
 * @summary javap invoked without args does not print help screen
 * @modules jdk.jdeps/com.sun.tools.javap
 */

import java.io.*;
import java.util.zip.*;

public class T4876942 {
    public static void main(String[] args) throws Exception {
        new T4876942().run();
    }

    public void run() throws IOException {
        String output = javap();
        verify(output, "-public", "-protected", "-private"); // check that some of the options are listed

        if (errors > 0)
            throw new Error(errors + " found.");
    }

    String javap(String... args) {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        //sun.tools.javap.Main.entry(args);
        int rc = com.sun.tools.javap.Main.run(args, out);
        if (rc != (args.length == 0 ? 2 : 0))
            throw new Error("javap failed. rc=" + rc);
        out.close();
        return sw.toString();
    }

    void verify(String output, String... expects) {
        for (String expect: expects) {
            if (output.indexOf(expect)< 0)
                error(expect + " not found");
        }
    }

    void error(String msg) {
        System.err.println(msg);
        errors++;
    }

    int errors;
}
