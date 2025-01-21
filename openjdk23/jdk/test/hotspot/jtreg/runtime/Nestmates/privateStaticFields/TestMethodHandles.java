/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8046171
 * @summary Test access to private static fields between nestmates and nest-host
 *          using different flavours of named nested types using MethodHandles
 * @run main TestMethodHandles
 */

import java.lang.invoke.*;
import static java.lang.invoke.MethodHandles.*;

import java.lang.reflect.Field;

public class TestMethodHandles {

    // private static field of nest-host for nestmates to access
    private static int priv_field;

    // public constructor so we aren't relying on private access
    public TestMethodHandles() {}

    // Methods that will access private static fields of nestmates

    // NOTE: No InnerNested calls in this test because non-static nested types
    // can't have static fields. Also no StaticIface calls as static interface
    // fields must be public (and final)

    void access_priv(TestMethodHandles o) throws Throwable {
        MethodHandle mh = lookup().findStaticGetter(o.getClass(), "priv_field", int.class);
        priv_field = (int) mh.invoke();
        priv_field = (int) mh.invokeExact();
        mh = lookup().findStaticSetter(o.getClass(), "priv_field", int.class);
        mh.invoke(2);
        mh.invokeExact(3);
    }
    void access_priv(StaticNested o) throws Throwable {
        MethodHandle mh = lookup().findStaticGetter(o.getClass(), "priv_field", int.class);
        priv_field = (int) mh.invoke();
        priv_field = (int) mh.invokeExact();
        mh = lookup().findStaticSetter(o.getClass(), "priv_field", int.class);
        mh.invoke(2);
        mh.invokeExact(3);
    }

    // The various nestmates

    static interface StaticIface {

        // Methods that will access private static fields of nestmates

        default void access_priv(TestMethodHandles o) throws Throwable {
            MethodHandle mh = lookup().findStaticGetter(o.getClass(), "priv_field", int.class);
            int priv_field = (int) mh.invoke();
            priv_field = (int) mh.invokeExact();
            mh = lookup().findStaticSetter(o.getClass(), "priv_field", int.class);
            mh.invoke(2);
            mh.invokeExact(3);
        }
        default void access_priv(StaticNested o) throws Throwable {
            MethodHandle mh = lookup().findStaticGetter(o.getClass(), "priv_field", int.class);
            int priv_field = (int) mh.invoke();
            priv_field = (int) mh.invokeExact();
            mh = lookup().findStaticSetter(o.getClass(), "priv_field", int.class);
            mh.invoke(2);
            mh.invokeExact(3);
        }
    }

    static class StaticNested {

        private static int priv_field;

        // public constructor so we aren't relying on private access
        public StaticNested() {}

        // Methods that will access private static fields of nestmates

        void access_priv(TestMethodHandles o) throws Throwable {
            MethodHandle mh = lookup().findStaticGetter(o.getClass(), "priv_field", int.class);
            priv_field = (int) mh.invoke();
            priv_field = (int) mh.invokeExact();
            mh = lookup().findStaticSetter(o.getClass(), "priv_field", int.class);
            mh.invoke(2);
            mh.invokeExact(3);
        }
        void access_priv(StaticNested o) throws Throwable {
            MethodHandle mh = lookup().findStaticGetter(o.getClass(), "priv_field", int.class);
            priv_field = (int) mh.invoke();
            priv_field = (int) mh.invokeExact();
            mh = lookup().findStaticSetter(o.getClass(), "priv_field", int.class);
            mh.invoke(2);
            mh.invokeExact(3);
        }
    }

    class InnerNested {

        // public constructor so we aren't relying on private access
        public InnerNested() {}

        void access_priv(TestMethodHandles o) throws Throwable {
            MethodHandle mh = lookup().findStaticGetter(o.getClass(), "priv_field", int.class);
            priv_field = (int) mh.invoke();
            priv_field = (int) mh.invokeExact();
            mh = lookup().findStaticSetter(o.getClass(), "priv_field", int.class);
            mh.invoke(2);
            mh.invokeExact(3);
        }
        void access_priv(StaticNested o) throws Throwable {
            MethodHandle mh = lookup().findStaticGetter(o.getClass(), "priv_field", int.class);
            priv_field = (int) mh.invoke();
            priv_field = (int) mh.invokeExact();
            mh = lookup().findStaticSetter(o.getClass(), "priv_field", int.class);
            mh.invoke(2);
            mh.invokeExact(3);
        }
    }

    public static void main(String[] args) throws Throwable {
        TestMethodHandles o = new TestMethodHandles();
        StaticNested s = new StaticNested();
        InnerNested i = o.new InnerNested();
        StaticIface intf = new StaticIface() {};

        o.access_priv(new TestMethodHandles());
        o.access_priv(s);

        s.access_priv(o);
        s.access_priv(new StaticNested());

        i.access_priv(o);
        i.access_priv(s);

        intf.access_priv(o);
        intf.access_priv(s);
    }
}
