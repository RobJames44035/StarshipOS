/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.lang.classfile.*;
import java.lang.annotation.*;
import java.util.ArrayList;

/*
 * @test
 * @bug 8136419 8200301
 * @summary test that type annotations on entities in initializers are emitted to classfile
 * @compile -XDdeduplicateLambdas=false InstanceInitializer.java
 * @run main InstanceInitializer
 */

public class InstanceInitializer extends ClassfileTestHelper {
    public static void main(String[] args) throws Exception {
        new InstanceInitializer().run();
    }

    public void run() throws Exception {
        expected_tinvisibles = 4;
        expected_tvisibles = 0;

        ClassModel cm = getClassFile("InstanceInitializer$Test.class");
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
        @interface T {}

        {
            @T String s = null;
            Runnable r = () -> new ArrayList<@T String>();
        }
        @T String s = null;
        Runnable r = () -> new ArrayList<@T String>();
    }
}
