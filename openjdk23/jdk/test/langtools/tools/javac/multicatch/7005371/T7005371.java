/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7005371
 * @summary  Multicatch: assertion error while generating LocalVariableTypeTable attribute
 * @compile -g SubTest.java
 * @run main T7005371
 */

import java.lang.classfile.*;
import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.attribute.LocalVariableTypeTableAttribute;

import java.io.*;

public class T7005371 {

    static final String SUBTEST_NAME = SubTest.class.getName() + ".class";
    static final String TEST_METHOD_NAME = "test";
    static final int LVT_LENGTH = 1;
    static final String LVT_SIG_TYPE = "Ljava/util/List<Ljava/lang/String;>;";


    public static void main(String... args) throws Exception {
        new T7005371().run();
    }

    public void run() throws Exception {
        String workDir = System.getProperty("test.classes");
        System.out.println(workDir);
        File compiledTest = new File(workDir, SUBTEST_NAME);
        verifyLocalVariableTypeTableAttr(compiledTest);
    }

    void verifyLocalVariableTypeTableAttr(File f) {
        System.err.println("verify: " + f);
        try {
            ClassModel cf = ClassFile.of().parse(f.toPath());
            MethodModel testMethod = null;
            for (MethodModel m : cf.methods()) {
                if (m.methodName().equalsString(TEST_METHOD_NAME)) {
                    testMethod = m;
                    break;
                }
            }
            if (testMethod == null) {
                throw new Error("Missing method: " + TEST_METHOD_NAME);
            }
            CodeAttribute code = testMethod.findAttribute(Attributes.code()).orElse(null);
            if (code == null) {
                throw new Error("Missing Code attribute for method: " + TEST_METHOD_NAME);
            }
            LocalVariableTypeTableAttribute lvt_table = code.findAttribute(Attributes.localVariableTypeTable()).orElse(null);
            if (lvt_table == null) {
                throw new Error("Missing LocalVariableTypeTable attribute for method: " + TEST_METHOD_NAME);
            }
            if (lvt_table.localVariableTypes().size() != LVT_LENGTH) {
                throw new Error("LocalVariableTypeTable has wrong size" +
                        "\nfound: " + lvt_table.localVariableTypes().size() +
                        "\nrequired: " + LVT_LENGTH);
            }
            String sig = lvt_table.localVariableTypes().get(0).signature().stringValue();

            if (sig == null || !sig.equals(LVT_SIG_TYPE)) {
                throw new Error("LocalVariableTypeTable has wrong signature" +
                        "\nfound: " + sig +
                        "\nrequired: " + LVT_SIG_TYPE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("error reading " + f +": " + e);
        }
    }
}
