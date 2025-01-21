/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary  check that javac does not generate bridge methods for defaults
 */

import java.lang.classfile.*;

import java.io.*;

public class TestNoBridgeOnDefaults {

    interface A<X> {
        default <Y> A<X> m(X x, Y y) { return Impl.<X,Y>m1(this, x, y); }
    }

    static abstract class B<X> implements A<X> { }

    interface C<X> extends A<X> {
        default <Y> C<X> m(X x, Y y) { return Impl.<X,Y>m2(this, x, y); }
    }

    static abstract class D<X> extends B<X> implements C<X> { }

    static class Impl {
       static <X, Y> A<X> m1(A<X> rec, X x, Y y) { return null; }
       static <X, Y> C<X> m2(C<X> rec, X x, Y y) { return null; }
    }

    static final String[] SUBTEST_NAMES = { B.class.getName() + ".class", D.class.getName() + ".class" };
    static final String TEST_METHOD_NAME = "m";

    public static void main(String... args) throws Exception {
        new TestNoBridgeOnDefaults().run();
    }

    public void run() throws Exception {
        String workDir = System.getProperty("test.classes");
        for (int i = 0 ; i < SUBTEST_NAMES.length ; i ++) {
            File compiledTest = new File(workDir, SUBTEST_NAMES[i]);
            checkNoBridgeOnDefaults(compiledTest);
        }
    }

    void checkNoBridgeOnDefaults(File f) {
        System.err.println("check: " + f);
        try {
            ClassModel cf = ClassFile.of().parse(f.toPath());
            for (MethodModel m : cf.methods()) {
                String mname = m.methodName().stringValue();
                if (mname.equals(TEST_METHOD_NAME)) {
                    throw new Error("unexpected bridge method found " + m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("error reading " + f +": " + e);
        }
    }
}
