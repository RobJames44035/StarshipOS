/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8033735
 * @summary check backtrace field introspection
 * @modules java.base/jdk.internal.misc:open
 * @modules java.base/java.lang:open
 * @run main ThrowableIntrospectionSegfault
 */

import java.lang.reflect.*;

public class ThrowableIntrospectionSegfault {
    public static void main(java.lang.String[] unused) {
        // Construct a throwable object.
        Throwable throwable = new Throwable();
        throwable.fillInStackTrace();

        // Retrieve a reflection handle to the private backtrace field.
        Class class1 = throwable.getClass();
        Field field;
        try {
            field = class1.getDeclaredField("backtrace");
        }
        catch (NoSuchFieldException e) {
            System.err.println("Can't retrieve field handle Throwable.backtrace: " + e.toString());
            return;
        }
        field.setAccessible(true);

        // Retrieve the value of the backtrace field.
        Object backtrace;
        try {
            backtrace = field.get(throwable);
        }
        catch (IllegalAccessException e) {
            System.err.println( "Can't retrieve field value for Throwable.backtrace: " + e.toString());
            return;
        }

        try {

            // Retrieve the class of throwable.backtrace[0][0].
            Class class2 = ((Object[]) ((Object[]) backtrace)[2])[0].getClass();

            // Segfault occurs while executing this line, to retrieve the name of
            // this class.
            String class2Name = class2.getName();

            System.err.println("class2Name=" + class2Name);
            return;  // pass!   Passes if it doesn't crash.
        } catch (ClassCastException e) {
            // Passes if it doesn't crash. Also if the backtrace changes this test might get
            // ClassCastException and that's ok too.
            System.out.println("Catch exception " + e);
            return;  // pass!   Passes if it doesn't crash.
        }
    }
}
