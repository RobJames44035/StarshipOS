/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8009360
 * @summary AssertionError from type annotation on member of anonymous class
 * @compile T8009360.java
 */
import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

class Test1<T> {
    Object mtest( Test1<T> t){ return null; }
    public void test() {
        mtest( new Test1<T>() {
                @A String data1 = "test";    // ok
                @A @A String data2 = "test"; // ok
                @A @B String data3 = "test"; // was AssertionError
                @B @C String data4 = "test"; // was AssertionError
           });
   }
}

@Target({TYPE_USE,FIELD}) @Repeatable( AC.class) @interface A { }
@Target({TYPE_USE,FIELD}) @interface AC { A[] value(); }
@Target({TYPE_USE}) @interface B { }
@Target({TYPE_USE, FIELD}) @interface C { }
