/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8336491
 * @summary Verify that void returning expression lambdas don't box their result
 * @modules jdk.compiler
 *          jdk.jdeps/com.sun.tools.javap
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;

public class VoidReturnBoxing {

    public static void main(String[] args) {
        new VoidReturnBoxing().run();
    }

    void run() {
        Path path = Path.of(System.getProperty("test.classes"), "T.class");
        StringWriter s;
        String out;
        try (PrintWriter pw = new PrintWriter(s = new StringWriter())) {
            com.sun.tools.javap.Main.run(new String[] {"-p", "-c", path.toString()}, pw);
            out = s.toString();
        }
        if (out.contains("java/lang/Integer.valueOf")) {
            throw new AssertionError(
                    "Unnecessary boxing of void returning expression lambda result:\n\n" + out);
        }
    }
}

class T {
    int g() {
        return 0;
    }

    Runnable r = () -> g();
}
