/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8067422
 * @summary Check that the lambda names are not unnecessarily unstable
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main TestNonSerializableLambdaNameStability
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.tools.StandardLocation;

import java.lang.classfile.*;

import toolbox.JavacTask;
import toolbox.ToolBox;

public class TestNonSerializableLambdaNameStability {

    public static void main(String... args) throws Exception {
        new TestNonSerializableLambdaNameStability().run();
    }

    String lambdaSource = "public class L%d {\n" +
                          "    public static class A {\n" +
                          "        private Runnable r = () -> { };\n" +
                          "    }\n" +
                          "    public static class B {\n" +
                          "        private Runnable r = () -> { };\n" +
                          "    }\n" +
                          "    private Runnable r = () -> { };\n" +
                          "}\n";

    String expectedLambdaMethodName = "lambda$new$0";

    void run() throws Exception {
        List<String> sources = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            sources.add(String.format(lambdaSource, i));
        }

        ToolBox tb = new ToolBox();

        try (ToolBox.MemoryFileManager fm = new ToolBox.MemoryFileManager()) {
            new JavacTask(tb)
              .sources(sources.toArray(new String[sources.size()]))
              .fileManager(fm)
              .run();

            for (String file : fm.getFileNames(StandardLocation.CLASS_OUTPUT)) {
                byte[] fileBytes = fm.getFileBytes(StandardLocation.CLASS_OUTPUT, file);
                try (InputStream in = new ByteArrayInputStream(fileBytes)) {
                    boolean foundLambdaMethod = false;
                    ClassModel cf = ClassFile.of().parse(in.readAllBytes());
                    StringBuilder seenMethods = new StringBuilder();
                    String sep = "";
                    for (MethodModel m : cf.methods()) {
                        String methodName = m.methodName().stringValue();
                        if (expectedLambdaMethodName.equals(methodName)) {
                            foundLambdaMethod = true;
                            break;
                        }
                        seenMethods.append(sep);
                        seenMethods.append(methodName);
                        sep = ", ";
                    }

                    if (!foundLambdaMethod) {
                        throw new AbstractMethodError("Did not find the lambda method, " +
                                                      "found methods: " + seenMethods.toString());
                    }
                }
            }
        }
    }
}
