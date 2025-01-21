/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8046171
 * @summary Test access to private static methods between nestmates and nest-host
 *          using different flavours of named nested types using MethodHandles
 * @run main TestMethodHandles
 */


import java.lang.invoke.*;
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;

public class TestMethodHandles {

    static final MethodType INVOKE_T = MethodType.methodType(void.class);

    // Private static method of nest-host for nestmates to access
    private static void priv_static_invoke() {
        System.out.println("TestMethodHandles::priv_static_invoke");
    }

    // public constructor so we aren't relying on private access
    public TestMethodHandles() {}

    // Methods that will access private static methods of nestmates

    // NOTE: No InnerNested calls in this test because non-static nested types
    // can't have static methods

    void access_priv(TestMethodHandles o) throws Throwable {
        MethodHandle mh =
          lookup().findStatic(o.getClass(), "priv_static_invoke", INVOKE_T);
        mh.invoke();
        mh.invokeExact();
    }
    void access_priv(StaticNested o) throws Throwable {
        MethodHandle mh =
          lookup().findStatic(o.getClass(), "priv_static_invoke", INVOKE_T);
        mh.invoke();
        mh.invokeExact();
    }
    void access_priv(StaticIface o) throws Throwable {
        MethodHandle mh =
          lookup().findStatic(StaticIface.class, "priv_static_invoke", INVOKE_T);
        mh.invoke();
        mh.invokeExact();
    }

    // The various nestmates

    static interface StaticIface {

        private static void priv_static_invoke() {
            System.out.println("StaticIface::priv_static_invoke");
        }

        // Methods that will access private static methods of nestmates

        default void access_priv(TestMethodHandles o) throws Throwable {
          MethodHandle mh =
            lookup().findStatic(o.getClass(), "priv_static_invoke", INVOKE_T);
          mh.invoke();
          mh.invokeExact();
        }
        default void access_priv(StaticNested o) throws Throwable {
          MethodHandle mh =
            lookup().findStatic(o.getClass(), "priv_static_invoke", INVOKE_T);
          mh.invoke();
          mh.invokeExact();
        }
        default void access_priv(StaticIface o) throws Throwable {
          MethodHandle mh =
            lookup().findStatic(StaticIface.class, "priv_static_invoke", INVOKE_T);
          mh.invoke();
          mh.invokeExact();
        }
    }

    static class StaticNested {

        private static void priv_static_invoke() {
            System.out.println("StaticNested::priv_static_invoke");
        }

        // public constructor so we aren't relying on private access
        public StaticNested() {}

        // Methods that will access private static methods of nestmates

        void access_priv(TestMethodHandles o) throws Throwable {
            MethodHandle mh =
              lookup().findStatic(o.getClass(), "priv_static_invoke", INVOKE_T);
          mh.invoke();
          mh.invokeExact();
        }
        void access_priv(StaticNested o) throws Throwable {
            MethodHandle mh =
              lookup().findStatic(o.getClass(), "priv_static_invoke", INVOKE_T);
          mh.invoke();
          mh.invokeExact();
        }
        void access_priv(StaticIface o) throws Throwable {
            MethodHandle mh =
              lookup().findStatic(StaticIface.class, "priv_static_invoke", INVOKE_T);
          mh.invoke();
          mh.invokeExact();
        }
    }

    class InnerNested {

        // public constructor so we aren't relying on private access
        public InnerNested() {}

        void access_priv(TestMethodHandles o) throws Throwable {
            MethodHandle mh =
              lookup().findStatic(o.getClass(), "priv_static_invoke", INVOKE_T);
            mh.invoke();
            mh.invokeExact();
        }
        void access_priv(StaticNested o) throws Throwable {
            MethodHandle mh =
              lookup().findStatic(o.getClass(), "priv_static_invoke", INVOKE_T);
            mh.invoke();
            mh.invokeExact();
        }
        void access_priv(StaticIface o) throws Throwable {
            MethodHandle mh =
              lookup().findStatic(StaticIface.class, "priv_static_invoke", INVOKE_T);
            mh.invoke();
            mh.invokeExact();
        }
    }

    public static void main(String[] args) throws Throwable {
        TestMethodHandles o = new TestMethodHandles();
        StaticNested s = new StaticNested();
        InnerNested i = o.new InnerNested();
        StaticIface intf = new StaticIface() {};

        o.access_priv(new TestMethodHandles());
        o.access_priv(s);
        o.access_priv(intf);

        s.access_priv(o);
        s.access_priv(new StaticNested());
        s.access_priv(intf);

        i.access_priv(o);
        i.access_priv(s);
        i.access_priv(intf);

        intf.access_priv(o);
        intf.access_priv(s);
        intf.access_priv(new StaticIface(){});
    }
}
