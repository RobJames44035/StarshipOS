/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8046171
 * @summary Test access to private static methods between nestmates and nest-host
 *          using different flavours of named nested types
 * @run main TestInvokeStatic
 */

public class TestInvokeStatic {

    // Private static method of nest-host for nestmates to access
    private static void priv_static_invoke() {
        System.out.println("TestInvokeStatic::priv_static_invoke");
    }

    // public constructor so we aren't relying on private access
    public TestInvokeStatic() {}

    // Methods that will access private static methods of nestmates
    // We use the arguments for overloading purposes and use the fact
    // you can invoke a static method through an object reference for
    // convenience. Except for static interface methods of course.

    // NOTE: No InnerNested calls in this test because non-static nested types
    // can't have static method

    void access_priv(TestInvokeStatic o) {
        o.priv_static_invoke();
    }
    void access_priv(StaticNested o) {
        o.priv_static_invoke();
    }
    void access_priv(StaticIface o) {
        StaticIface.priv_static_invoke();
    }

    // The various nestmates

    static interface StaticIface {

        private static void priv_static_invoke() {
            System.out.println("StaticIface::priv_static_invoke");
        }

        // Methods that will access private static methods of nestmates

        default void access_priv(TestInvokeStatic o) {
            o.priv_static_invoke();
        }
        default void access_priv(StaticNested o) {
            o.priv_static_invoke();
        }
        default void access_priv(StaticIface o) {
            StaticIface.priv_static_invoke();
        }
    }

    static class StaticNested {

        private static void priv_static_invoke() {
            System.out.println("StaticNested::priv_static_invoke");
        }

        // public constructor so we aren't relying on private access
        public StaticNested() {}

        // Methods that will access private static methods of nestmates

        void access_priv(TestInvokeStatic o) {
            o.priv_static_invoke();
        }
        void access_priv(StaticNested o) {
            o.priv_static_invoke();
        }
        void access_priv(StaticIface o) {
            StaticIface.priv_static_invoke();
        }
    }

    class InnerNested {

        // public constructor so we aren't relying on private access
        public InnerNested() {}

        void access_priv(TestInvokeStatic o) {
            o.priv_static_invoke();
        }
        void access_priv(StaticNested o) {
            o.priv_static_invoke();
        }
        void access_priv(StaticIface o) {
            StaticIface.priv_static_invoke();
        }
    }

    public static void main(String[] args) {
        TestInvokeStatic o = new TestInvokeStatic();
        StaticNested s = new StaticNested();
        InnerNested i = o.new InnerNested();
        StaticIface intf = new StaticIface() {};

        o.access_priv(new TestInvokeStatic());
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
