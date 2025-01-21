/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

import java.io.*;
import java.lang.classfile.*;
import java.lang.classfile.attribute.*;

/*
 * @test Wildcards
 * @bug 6843077
 * @summary test that annotations target wildcards get emitted to classfile
 */
public class Wildcards {
    public static void main(String[] args) throws Exception {
        new Wildcards().run();
    }

    public void run() throws Exception {
        File javaFile = writeTestFile();
        File classFile = compileTestFile(javaFile);

        ClassModel cm = ClassFile.of().parse(classFile.toPath());
        test(cm);
        for (FieldModel fm : cm.fields()) {
            test(fm);
        }
        for (MethodModel mm: cm.methods()) {
            test(mm);
        }

        countAnnotations();

        if (errors > 0)
            throw new Exception(errors + " errors found");
        System.out.println("PASSED");
    }
    void test(AttributedElement m) {
        test(m, Attributes.runtimeVisibleTypeAnnotations());
        test(m, Attributes.runtimeInvisibleTypeAnnotations());
    }
    <T extends Attribute<T>> void test(AttributedElement m, AttributeMapper<T> attr_name) {
        Attribute<T> attr_instance = m.findAttribute(attr_name).orElse(null);
        if (attr_instance != null) {
            switch (attr_instance) {
                case RuntimeVisibleTypeAnnotationsAttribute tAttr -> {
                    all += tAttr.annotations().size();
                    visibles += tAttr.annotations().size();
                }
                case RuntimeInvisibleTypeAnnotationsAttribute tAttr -> {
                    all += tAttr.annotations().size();
                    invisibles += tAttr.annotations().size();
                }
                default -> throw new AssertionError();
            }
        }
    }
    File writeTestFile() throws IOException {
        File f = new File("Test.java");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
        out.println("import java.lang.annotation.*;");
        out.println("import java.util.*;");
        out.println("class Test { ");
        out.println("  @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})");
        out.println("  @interface A { }");

        out.println("  List<? extends @A Number> f;");

        out.println("  List<? extends @A Object> test(List<? extends @A Number> p) {");
        out.println("    List<? extends @A Object> l;");    // not counted... gets optimized away
        out.println("    return null;");
        out.println(" }");
        out.println("}");

        out.close();
        return f;
    }

    File compileTestFile(File f) {
        int rc = com.sun.tools.javac.Main.compile(new String[] {"-g", f.getPath() });
        if (rc != 0)
            throw new Error("compilation failed. rc=" + rc);
        String path = f.getPath();
        return new File(path.substring(0, path.length() - 5) + ".class");
    }

    void countAnnotations() {
        int expected_visibles = 0, expected_invisibles = 3;
        int expected_all = expected_visibles + expected_invisibles;

        if (expected_all != all) {
            errors++;
            System.err.println("expected " + expected_all
                    + " annotations but found " + all);
        }

        if (expected_visibles != visibles) {
            errors++;
            System.err.println("expected " + expected_visibles
                    + " visibles annotations but found " + visibles);
        }

        if (expected_invisibles != invisibles) {
            errors++;
            System.err.println("expected " + expected_invisibles
                    + " invisibles annotations but found " + invisibles);
        }

    }

    int errors;
    int all;
    int visibles;
    int invisibles;
}
