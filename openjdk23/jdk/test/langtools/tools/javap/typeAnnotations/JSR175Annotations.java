/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.io.*;
import java.lang.classfile.*;
import java.lang.classfile.Attributes;
import java.lang.classfile.attribute.*;

/*
 * @test JSR175Annotations
 * @bug 6843077
 * @summary test that only type annotations are recorded as such in classfile
 */

public class JSR175Annotations {
    public static void main(String[] args) throws Exception {
        new JSR175Annotations().run();
    }

    public void run() throws Exception {
        File javaFile = writeTestFile();
        File classFile = compileTestFile(javaFile);

        ClassModel cm = ClassFile.of().parse(classFile.toPath());
        for (MethodModel mm: cm.methods()) {
            test(mm);
        }
        for (FieldModel fm: cm.fields()) {
            test(fm);
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

    // test the result of AttributedElement.findAttribute according to expectations
    <T extends java.lang.classfile.Attribute<T>> void test(AttributedElement m, AttributeMapper<T> attr_name) {
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
        out.println("abstract class Test { ");
        out.println("  @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})");
        out.println("  @Retention(RetentionPolicy.RUNTIME)");
        out.println("  @interface A { }");
        out.println("  @A String m;");
        out.println("  @A String method(@A String a) {");
        out.println("    return a;");
        out.println("  }");
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
        int expected_visibles = 0, expected_invisibles = 0;
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
