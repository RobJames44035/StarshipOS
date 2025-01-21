/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6943289
 * @summary  Project Coin: Improved Exception Handling for Java (aka 'multicatch')
 * @run main Pos05
 */

import java.lang.classfile.*;
import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.instruction.ExceptionCatch;
import java.io.*;

public class Pos05 {

    static class Pos05sub {

        class A extends Exception {}
        class B extends Exception {}
        class C extends Exception {}

        void test(boolean b1, boolean b2) {
            try {
                if (b1) {
                    throw new A();
                }
                else if (b2) {
                    throw new B();
                }
                else {
                    throw new C();
                }
            }
            catch (final A | B | C ex) {
                System.out.println("Exception caught");
            }
        }
    }

    static final int TYPES_IN_MULTICATCH = 3;
    static final String SUBTEST_NAME = Pos05sub.class.getName() + ".class";
    static final String TEST_METHOD_NAME = "test";

    public static void main(String... args) throws Exception {
        new Pos05().run();
    }

    public void run() throws Exception {
        String workDir = System.getProperty("test.classes");
        File compiledTest = new File(workDir, SUBTEST_NAME);
        verifyMulticatchExceptionRanges(compiledTest);
    }

    void verifyMulticatchExceptionRanges(File f) {
        System.err.println("verify: " + f);
        try {
            int count = 0;
            ClassModel cf = ClassFile.of().parse(f.toPath());
            MethodModel testMethod = null;
            for (MethodModel m : cf.methods()) {
                if (m.methodName().equalsString(TEST_METHOD_NAME)) {
                    testMethod = m;
                    break;
                }
            }
            if (testMethod == null) {
                throw new Error("Test method not found");
            }
            CodeAttribute ea = testMethod.findAttribute(Attributes.code()).orElse(null);
            if (ea == null) {
                throw new Error("Code attribute for test() method not found");
            }
            ExceptionCatch firstExceptionTable = null;
            for (int i = 0 ; i < ea.exceptionHandlers().size(); i++) {
                if (firstExceptionTable == null) {
                    firstExceptionTable = ea.exceptionHandlers().get(i);
                }
                if (ea.exceptionHandlers().get(i).handler() != firstExceptionTable.handler() ||
                        ea.exceptionHandlers().get(i).tryStart() != firstExceptionTable.tryStart() ||
                        ea.exceptionHandlers().get(i).tryEnd() != firstExceptionTable.tryEnd()) {
                    throw new Error("Multiple overlapping catch clause found in generated code");
                }
                count++;
            }
            if (count != TYPES_IN_MULTICATCH) {
                throw new Error("Wrong number of exception data found: " + count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("error reading " + f +": " + e);
        }
    }
}
