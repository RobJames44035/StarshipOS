/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

import java.lang.annotation.*;
import java.io.*;
import java.net.URL;
import java.util.List;

import java.lang.classfile.*;

/*
 * @test
 * @bug 6843077 8006775
 * @summary test that typecasts annotation are emitted if only the cast
 *          expression is optimized away
 */

public class TypeCasts extends ClassfileTestHelper{
    public static void main(String[] args) throws Exception {
        new TypeCasts().run();
    }

    public void run() throws Exception {
        expected_tinvisibles = 4;
        expected_tvisibles = 0;
        ClassModel cm = getClassFile("TypeCasts$Test.class");
        test(cm);
        for (FieldModel fm : cm.fields()) {
            test(fm);
        }
        for (MethodModel mm: cm.methods()) {
            test(mm, true);
        }

        countAnnotations();

        if (errors > 0)
            throw new Exception(errors + " errors found");
        System.out.println("PASSED");
    }

    /*********************** Test class *************************/
    static class Test {
        @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
        @interface A {}

        void emit() {
            Object o = null;
            String s = null;

            String a0 = (@A String)o;
            Object a1 = (@A Object)o;

            String b0 = (@A String)s;
            Object b1 = (@A Object)s;
        }

        void alldeadcode() {
            Object o = null;

            if (false) {
                String a0 = (@A String)o;
            }
        }
    }
}
