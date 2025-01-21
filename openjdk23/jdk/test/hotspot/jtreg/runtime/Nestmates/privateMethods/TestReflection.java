/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8046171
 * @summary Test access to private methods between nestmates and nest-host
 *          using different flavours of named nested types using core reflection
 * @run main TestReflection
 */

// The first run will use NativeMethodAccessor and due to the limited number
// of calls we will not reach the inflation threshold.
// The second run disables inflation so we will use the GeneratedMethodAccessor
// instead. In this way both sets of Reflection classes are tested.

public class TestReflection {

    // Private method of nest-host for nestmates to access
    private void priv_invoke() {
        System.out.println("TestReflection::priv_invoke");
    }

    // public constructor so we aren't relying on private access
    public TestReflection() {}

    // Methods that will access private methods of nestmates

    void access_priv(TestReflection o) throws Throwable {
        o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
    }
    void access_priv(InnerNested o) throws Throwable {
        o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
    }
    void access_priv(StaticNested o) throws Throwable {
        o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
    }
    void access_priv(StaticIface o) throws Throwable {
        // Can't use o.getClass() as the method is not in that class
        StaticIface.class.getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
    }

    // The various nestmates

    static interface StaticIface {

        private void priv_invoke() {
            System.out.println("StaticIface::priv_invoke");
        }

        // Methods that will access private methods of nestmates

        default void access_priv(TestReflection o) throws Throwable {
            o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
        default void access_priv(InnerNested o) throws Throwable {
            o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
        default void access_priv(StaticNested o) throws Throwable {
            o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
        default void access_priv(StaticIface o) throws Throwable {
            // Can't use o.getClass() as the method is not in that class
            StaticIface.class.getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
    }

    static class StaticNested {

        private void priv_invoke() {
            System.out.println("StaticNested::priv_invoke");
        }

        // public constructor so we aren't relying on private access
        public StaticNested() {}

        // Methods that will access private methods of nestmates

        void access_priv(TestReflection o) throws Throwable {
            o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
        void access_priv(InnerNested o) throws Throwable {
            o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
        void access_priv(StaticNested o) throws Throwable {
            o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
        void access_priv(StaticIface o) throws Throwable {
            // Can't use o.getClass() as the method is not in that class
            StaticIface.class.getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
    }

    class InnerNested {

        private void priv_invoke() {
            System.out.println("InnerNested::priv_invoke");
        }

        // public constructor so we aren't relying on private access
        public InnerNested() {}

        void access_priv(TestReflection o) throws Throwable {
            o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
        void access_priv(InnerNested o) throws Throwable {
            o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
        void access_priv(StaticNested o) throws Throwable {
            o.getClass().getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
        void access_priv(StaticIface o) throws Throwable {
            // Can't use o.getClass() as the method is not in that class
            StaticIface.class.getDeclaredMethod("priv_invoke", new Class<?>[0]).invoke(o, new Object[0]);
        }
    }

    public static void main(String[] args) throws Throwable {
        TestReflection o = new TestReflection();
        StaticNested s = new StaticNested();
        InnerNested i = o.new InnerNested();
        StaticIface intf = new StaticIface() {};

        o.access_priv(new TestReflection());
        o.access_priv(i);
        o.access_priv(s);
        o.access_priv(intf);

        s.access_priv(o);
        s.access_priv(i);
        s.access_priv(new StaticNested());
        s.access_priv(intf);

        i.access_priv(o);
        i.access_priv(o.new InnerNested());
        i.access_priv(s);
        i.access_priv(intf);

        intf.access_priv(o);
        intf.access_priv(i);
        intf.access_priv(s);
        intf.access_priv(new StaticIface(){});
    }
}
