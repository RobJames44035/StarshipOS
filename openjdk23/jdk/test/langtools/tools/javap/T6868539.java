/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6868539 6868548 8035364
 * @summary javap should use current names for constant pool entries,
 *              remove spurious ';' from constant pool entries
 * @modules jdk.jdeps/com.sun.tools.javap
 */

import java.io.*;
import java.util.*;

public class T6868539

{
    public static void main(String... args) {
        new T6868539().run();
    }

    void run() {
        String output = javap("T6868539");
        verify(output, "Utf8 +java/lang/String");                                   // 1: Utf8
                                                                                    // 2: currently unused
        verify(output, "Integer +123456");                                          // 3: Integer
        verify(output, "Float +123456.0f");                                         // 4: Float
        verify(output, "Long +123456l");                                            // 5: Long
        verify(output, "Double +123456.0d");                                        // 6: Double
        verify(output, "Class +#[0-9]+ +// +T6868539");                             // 7: Class
        verify(output, "String +#[0-9]+ +// +not found");                           // 8: String
        verify(output, "Fieldref +#[0-9]+\\.#[0-9]+ +// +T6868539.errors:I");       // 9: Fieldref
        verify(output, "Methodref +#[0-9]+\\.#[0-9]+ +// +T6868539.run:\\(\\)V");   // 10: Methodref
        verify(output, "InterfaceMethodref +#[0-9]+\\.#[0-9]+ +// +java/lang/Runnable\\.run:\\(\\)V");
                                                                                    // 11: InterfaceMethodref
        verify(output, "NameAndType +#[0-9]+:#[0-9]+ +// +run:\\(\\)V");            // 12: NameAndType
        if (errors > 0)
            throw new Error(errors + " found.");
    }

    String notFound = " not found";

    void verify(String output, String... expects) {
        for (String expect: expects) {
            if (!output.matches("(?s).*" + expect + ".*"))
                error(expect + notFound);
        }
    }

    void error(String msg) {
        System.err.println(msg);
        errors++;
    }

    int errors;

    String javap(String className) {
        String testClasses = System.getProperty("test.classes", ".");
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        String[] args = { "-v", "-classpath", testClasses, className };
        int rc = com.sun.tools.javap.Main.run(args, out);
        if (rc != 0)
            throw new Error("javap failed. rc=" + rc);
        out.close();
        String output = sw.toString();
        System.out.println("class " + className);
        System.out.println(output);
        return output;
    }

    int i = 123456;
    float f = 123456.f;
    double d = 123456.;
    long l = 123456L;

    void m(Runnable r) { r.run(); }
}

