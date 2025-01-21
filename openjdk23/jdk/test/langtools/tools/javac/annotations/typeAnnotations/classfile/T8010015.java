/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @summary Wrong classfile attribution in inner class of lambda expression.
 * @bug 8010015
 */

import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.classfile.*;

/*
 * A type-annotations on a field in an inner class not in a lambda expression
 * results in RuntimeTypeAnnotations_attibute and RuntimeAnnotations_attribute.
 * On a field in an innner class in a lambda expression, it leaves off the
 * RuntimeAnnotations_attribute.
 */
public class T8010015 extends ClassfileTestHelper{
    public static void main(String[] args) throws Exception {
        new T8010015().run();
    }

    public void run() throws Exception {
        expected_tvisibles = 1;
        expected_visibles = 1;
        ClassModel cm = getClassFile("T8010015$Test$1innerClass.class");
        for (FieldModel fm : cm.fields()) {
            test(fm);
        }
        countAnnotations();

        if (errors > 0)
            throw new Exception(errors + " errors found");
        System.out.println("PASSED");
    }

    /*********************** Test class **************************/
    interface MapFun<T, R> { R m( T n); }
    static class Test {
        MapFun<Class<?>,String> cs;
        void test() {
            cs = c -> {
                     class innerClass {
                         @A Class<?> icc = null;
                         innerClass(Class<?> _c) { icc = _c; }
                         String getString() { return icc.toString(); }
                     }
                     return new innerClass(c).getString();
            };
            System.out.println("cs.m : " + cs.m(Integer.class));
        }

    public static void main(String... args) {new Test().test(); }
    }
    @Retention(RUNTIME) @Target({TYPE_USE,FIELD}) @interface A { }
}
