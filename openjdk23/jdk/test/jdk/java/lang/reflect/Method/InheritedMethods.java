/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
   @bug 4471738
   @summary Failure to properly traverse class hierarchy in Class.getMethod()
*/

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class InheritedMethods {
    public static void main(String[] args) throws Exception { new InheritedMethods(); }
    InheritedMethods() throws Exception {
        Class c = Foo.class;
        Method m = c.getMethod("removeAll", new Class[] { Collection.class });
        if (m.getDeclaringClass() != java.util.List.class) {
          throw new RuntimeException("TEST FAILED");
        }
    }
    interface Foo extends List { }
}
