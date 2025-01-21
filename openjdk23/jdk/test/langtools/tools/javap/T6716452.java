/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test 6716452
 * @summary need a method to get an index of an attribute
 */

import java.io.*;
import java.nio.file.Files;

import java.lang.classfile.*;
import java.lang.classfile.attribute.*;

public class T6716452 {
    public static void main(String[] args) throws Exception {
        new T6716452().run();
    }

    public void run() throws Exception {
        File javaFile = writeTestFile();
        File classFile = compileTestFile(javaFile);
        ClassModel cm = ClassFile.of().parse(classFile.toPath());
        for (MethodModel mm: cm.methods()) {
            test(mm);
        }

        if (errors > 0)
            throw new Exception(errors + " errors found");
    }

    void test(MethodModel mm) {
        test(mm, Attributes.code(), CodeAttribute.class);
        test(mm, Attributes.exceptions(), ExceptionsAttribute.class);
    }

    // test the result of MethodModel.findAttribute, MethodModel.attributes().indexOf() according to expectations
    // encoded in the method's name
    <T extends Attribute<T>> void test(MethodModel mm, AttributeMapper<T> attr, Class<?> c) {
        Attribute<T> attr_instance = mm.findAttribute(attr).orElse(null);
        int index = mm.attributes().indexOf(attr_instance);
        String mm_name = mm.methodName().stringValue();
        System.err.println("Method " + mm_name + " name:" + attr.name() + " index:" + index + " class: " + c);
        boolean expect = (mm_name.equals("<init>") && attr.name().equals("Code"))
                || (mm_name.contains(attr.name()));
        boolean found = (index != -1);
        if (expect) {
            if (found) {
                if (!c.isAssignableFrom(mm.attributes().get(index).getClass())) {
                    error(mm + ": unexpected attribute found,"
                            + " expected " + c.getName()
                            + " found " + mm.attributes().get(index).attributeName().stringValue());
                }
            } else {
                error(mm + ": expected attribute " + attr.name() + " not found");
            }
        } else {
            if (found) {
                error(mm + ": unexpected attribute " + attr.name());
            }
        }
    }

    File writeTestFile() throws IOException {
        File f = new File("Test.java");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
        out.println("abstract class Test { ");
        out.println("  abstract void m();");
        out.println("  void m_Code() { }");
        out.println("  abstract void m_Exceptions() throws Exception;");
        out.println("  void m_Code_Exceptions() throws Exception { }");
        out.println("}");
        out.close();
        return f;
    }

    File compileTestFile(File f) {
        int rc = com.sun.tools.javac.Main.compile(new String[] { "-g", f.getPath() });
        if (rc != 0)
            throw new Error("compilation failed. rc=" + rc);
        String path = f.getPath();
        return new File(path.substring(0, path.length() - 5) + ".class");
    }

    void error(String msg) {
        System.err.println("error: " + msg);
        errors++;
    }

    int errors;
}
