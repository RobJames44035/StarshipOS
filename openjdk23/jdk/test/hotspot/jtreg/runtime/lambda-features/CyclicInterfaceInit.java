/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8163969
 * @summary Interface initialization was crashing on this because the wrong class was getting
 * initialization error.
 * @run main CyclicInterfaceInit
 */
/**
 * This snippet crashes with
 * - Java(TM) SE Runtime Environment (8.0_101-b13) (build 1.8.0_101-b13)
 */
public class CyclicInterfaceInit {

    interface Base {
        static final Object CONST = new Target(){}.someMethod();

        default void important() {
            // Super interfaces with default methods get initialized (JLS 12.4.1)
        }
    }

   static boolean out(String c) {
       System.out.println("initializing " + c);
       return true;
    }

    interface Target extends Base {
        boolean v = CyclicInterfaceInit.out("Target");
        default Object someMethod() {
            throw new RuntimeException();
        }
        // Target can be fully initialized before initializating Base because Target doesn't
        // initiate the initialization of Base.
    }

    static class InnerBad implements Target {}

    public static void main(String[] args) {
        try {
          new Target() {};  // Creates inner class that causes initialization of super interfaces
        } catch (ExceptionInInitializerError e) {
          System.out.println("ExceptionInInitializerError thrown as expected");
        }
        // Try again, InnerBad instantiation should throw NoClassdefFoundError
        // because Base is marked erroneous due to previous exception during initialization
        try {
          InnerBad ig = new InnerBad();
          throw new RuntimeException("FAILED- initialization of InnerBad should throw NCDFE");
        } catch (NoClassDefFoundError e) {
          System.out.println("NoClassDefFoundError thrown as expected");
        }
        // Target is already initialized.
        System.out.println("Target.v is " + Target.v);
        // shouldn't throw any exceptions.
    }
}
