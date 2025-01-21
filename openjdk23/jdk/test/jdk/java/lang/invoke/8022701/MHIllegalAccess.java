/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8022701
 * @summary Illegal access exceptions via methodhandle invocations threw wrong error.
 * @compile -XDignore.symbol.file BogoLoader.java InvokeSeveralWays.java MHIllegalAccess.java MethodSupplier.java
 * @run main/othervm MHIllegalAccess
 */

import java.lang.classfile.AccessFlags;
import java.lang.classfile.ClassTransform;
import java.lang.classfile.MethodModel;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.classfile.ClassFile.ACC_PRIVATE;
import static java.lang.classfile.ClassFile.ACC_PROTECTED;
import static java.lang.classfile.ClassFile.ACC_PUBLIC;

public class MHIllegalAccess {

   public static void main(String[] args) throws Throwable {
      System.out.println("Classpath is " + System.getProperty("java.class.path"));
      System.out.println();

      /**
       * Make method m be private to provoke an IllegalAccessError.
       */
      var privatize = ClassTransform.transformingMethods(m -> m.methodName().equalsString("m"), (mb, me) -> {
          if (me instanceof AccessFlags af) {
              mb.withFlags((af.flagsMask() | ACC_PRIVATE) & ~ (ACC_PUBLIC | ACC_PROTECTED));
          } else {
              mb.accept(me);
          }
      });

     /**
       * Rename method m as nemo to provoke a NoSuchMethodError.
       */
     ClassTransform changeName = (cb, ce) -> {
         if (ce instanceof MethodModel mm && mm.methodName().equalsString("m")) {
             cb.withMethod("nemo", mm.methodTypeSymbol(), mm.flags().flagsMask(), mm::forEach);
         } else {
             cb.accept(ce);
         }
     };

      int failures = 0;
      failures += testOneError(privatize, args, IllegalAccessError.class);
      failures += testOneError(changeName, args, NoSuchMethodError.class);
      if (failures > 0) {
          System.out.println("Saw " + failures + " failures, see standard out for details");
          throw new Error("FAIL test");
      }
   }

   /**
    *
    * @param vm VisitorMaker, to be stored in a table and passed to a BogoLoader
    * @param args A copy of the main args, to be passed on to InvokeSeveralWays.test
    * @param expected The class of the exception that should be thrown after
    *                 attempted invocation of MethodSupplier.m.
    * @throws ClassNotFoundException
    * @throws Throwable
    */
    private static int testOneError(ClassTransform vm, String[] args, Class<?> expected) throws ClassNotFoundException, Throwable {
      var replace = new HashMap<String, ClassTransform>();
      replace.put("MethodSupplier", vm);

      HashSet<String> in_bogus = new HashSet<String>();
        in_bogus.add("InvokeSeveralWays");
        in_bogus.add("MyFunctionalInterface");
        in_bogus.add("Invoker");

        BogoLoader bl = new BogoLoader(in_bogus, replace);
        Class<?> isw = bl.loadClass("InvokeSeveralWays");
        Object[] arg_for_args = new Object[2];
        arg_for_args[0] = args;
        arg_for_args[1] = expected;
        try {
            Object result = isw.getMethod("test", String[].class, Class.class).invoke(null, arg_for_args);
            return (Integer)result;
        } catch (InvocationTargetException e) {
            Throwable th = e.getCause();
            throw th == null ? e : th;
        }
    }
}
