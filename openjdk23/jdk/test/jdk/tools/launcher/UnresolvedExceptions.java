/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4529320
 * @modules jdk.compiler
 *          jdk.zipfs
 * @compile -XDignore.symbol.file UnresolvedExceptions.java
 * @run main UnresolvedExceptions
 * @summary Verifying jvm won't segv if exception not available
 * @author Joseph D. Darcy, ksrini
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class UnresolvedExceptions extends TestHelper {

    public static void main(String... args) throws Exception {
        final String fname = "Foo";
        List<String> buffer = new ArrayList<>();
        buffer.add("public class " + fname + " {");
        buffer.add("    public static void main(String[] argv) throws "
                       + "Foo.SomeException {");
        buffer.add("        System.exit(0);");
        buffer.add("    }");
        buffer.add("    static class SomeException extends RuntimeException{}");
        buffer.add("}");

        File testJavaFile = new File("Foo" + JAVA_FILE_EXT);
        createFile(testJavaFile, buffer);
        compile(testJavaFile.getName());
        TestResult tr = doExec(javaCmd, "-cp", ".", fname);
        if (!tr.isOK()) {
            System.out.println(tr);
            throw new RuntimeException("java -cp ... failed");
    }
}
}
