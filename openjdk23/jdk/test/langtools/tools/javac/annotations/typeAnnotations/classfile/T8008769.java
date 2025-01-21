/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @summary Repeated type-annotations on type parm of local variable
 *          are not written to classfile.
 * @bug 8008769
 */
import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.classfile.*;

public class T8008769 extends ClassfileTestHelper{
    public static void main(String[] args) throws Exception {
        new T8008769().run();
    }

    public void run() throws Exception {
        expected_tvisibles = 4;
        ClassModel cm = getClassFile("T8008769$Test.class");
        for (MethodModel mm : cm.methods()) {
            test(mm, true);
        }
        countAnnotations();

        if (errors > 0)
            throw new Exception(errors + " errors found");
        System.out.println("PASSED");
    }

    /*********************** Test class *************************/
    static class Test<T> {
        public void test() {
            Test<@A @B String>    t0 = new Test<>(); // 2 ok
            Test<@B @B String>    t1 = new Test<>(); // 1 missing
            Test<@A @A @A String> t2 = new Test<>(); // 1 missing
       }
    }
    @Retention(RUNTIME) @Target(TYPE_USE) @Repeatable( AC.class ) @interface A { }
    @Retention(RUNTIME) @Target(TYPE_USE) @Repeatable( BC.class ) @interface B { }
    @Retention(RUNTIME) @Target(TYPE_USE) @interface AC { A[] value(); }
    @Retention(RUNTIME) @Target(TYPE_USE) @interface BC { B[] value(); }
}
