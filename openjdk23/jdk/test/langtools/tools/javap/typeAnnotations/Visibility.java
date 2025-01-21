/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.io.*;
import java.lang.classfile.*;
import java.lang.classfile.Attributes;
import java.lang.classfile.attribute.*;

/*
 * @test Visibility
 * @bug 6843077
 * @summary test that type annotations are recorded in the classfile
 */

public class Visibility {
    public static void main(String[] args) throws Exception {
        new Visibility().run();
    }

    public void run() throws Exception {
        File javaFile = writeTestFile();
        File classFile = compileTestFile(javaFile);

        ClassModel cm = ClassFile.of().parse(classFile.toPath());
        for (MethodModel mm: cm.methods()) {
            test(mm);
        }

        countAnnotations();

        if (errors > 0)
            throw new Exception(errors + " errors found");
        System.out.println("PASSED");
    }

    void test(MethodModel mm) {
        test(mm, Attributes.runtimeVisibleTypeAnnotations());
        test(mm, Attributes.runtimeInvisibleTypeAnnotations());
    }

    // test the result of mm.findAttribute according to expectations
    // encoded in the method's name
    <T extends Attribute<T>> void test(MethodModel mm, AttributeMapper<T> attr_name) {
        Attribute<T> attr_instance = mm.findAttribute(attr_name).orElse(null);
        if (attr_instance != null) {
            switch (attr_instance) {
                case RuntimeInvisibleTypeAnnotationsAttribute tAttr -> {
                    all += tAttr.annotations().size();
                    invisibles += tAttr.annotations().size();
                }
                case RuntimeVisibleTypeAnnotationsAttribute tAttr -> {
                    all += tAttr.annotations().size();
                    visibles += tAttr.annotations().size();
                }
                default -> throw new AssertionError();
            }
        }
    }

    File writeTestFile() throws IOException {
        File f = new File("Test.java");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
        out.println("import java.lang.annotation.ElementType;");
        out.println("import java.lang.annotation.Retention;");
        out.println("import java.lang.annotation.RetentionPolicy;");
        out.println("import java.lang.annotation.Target;");
        out.println("abstract class Test { ");
        // visible annotations: RUNTIME
        out.println("  @Retention(RetentionPolicy.RUNTIME)");
        out.println("  @Target(ElementType.TYPE_USE)");
        out.println("  @interface A { }");
        out.println("  void visible(@A Test this) { }");

        // invisible annotations: CLASS
        out.println("  @Retention(RetentionPolicy.CLASS)");
        out.println("  @Target(ElementType.TYPE_USE)");
        out.println("  @interface B { }");
        out.println("  void invisible(@B Test this) { }");

        // source annotations
        out.println("  @Retention(RetentionPolicy.SOURCE)");
        out.println("  @Target(ElementType.TYPE_USE)");
        out.println("  @interface C { }");
        out.println("  void source(@C Test this) { }");

        // default visibility: CLASS
        out.println("  @Target(ElementType.TYPE_USE)");
        out.println("  @interface D { }");
        out.println("  void def(@D Test this) { }");
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
        int expected_all = 3, expected_visibles = 1, expected_invisibles = 2;

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
