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
 * @bug 6917130 8006775
 * @summary test that optimized away annotations are not emited to classfile
 */

public class DeadCode extends ClassfileTestHelper {
    public static void main(String[] args) throws Exception {
        new DeadCode().run();
    }

    public void run() throws Exception {
        expected_tinvisibles = 1;
        expected_tvisibles = 0;
        ClassModel cm = getClassFile("DeadCode$Test.class");
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

        void test() {
            List<? extends @A Object> o = null;
            o.toString();

            @A String m;
            if (false) {
                @A String a;
                @A String b = "m";
                b.toString();
                List<? extends @A Object> c = null;
                c.toString();
            }
        }
    }

}
