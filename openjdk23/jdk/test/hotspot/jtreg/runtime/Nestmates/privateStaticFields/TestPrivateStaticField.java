/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8046171
 * @summary Test access to private static fields between nestmates and nest-host
 *          using different flavours of named nested types
 * @run main TestPrivateStaticField
 */

public class TestPrivateStaticField {

    // Private static field of nest-host for nestmates to access
    private static int priv_field;

    // public constructor so we aren't relying on private access
    public TestPrivateStaticField() {}

    // Methods that will access private static fields of nestmates
    // We use the arguments for overloading purposes and use the fact
    // you can access a static field through an object reference for
    // convenience.

    // NOTE: No InnerNested calls in this test because non-static nested types
    // can't have static fields. Also no StaticIface calls as static interface
    // fields must be public (and final)

    void access_priv(TestPrivateStaticField o) {
        priv_field = o.priv_field++;
    }
    void access_priv(StaticNested o) {
        priv_field = o.priv_field++;
    }

    // The various nestmates

    static interface StaticIface {

        // Methods that will access private static fields of nestmates

        default void access_priv(TestPrivateStaticField o) {
            int priv_field = o.priv_field++;
        }
        default void access_priv(StaticNested o) {
            int priv_field = o.priv_field++;
        }
    }

    static class StaticNested {

        private static int priv_field;

        // public constructor so we aren't relying on private access
        public StaticNested() {}

        // Methods that will access private static fields of nestmates

        void access_priv(TestPrivateStaticField o) {
            priv_field = o.priv_field++;
        }
        void access_priv(StaticNested o) {
            priv_field = o.priv_field++;
        }
    }

    class InnerNested {

        // public constructor so we aren't relying on private access
        public InnerNested() {}

        void access_priv(TestPrivateStaticField o) {
            int priv_field = o.priv_field++;
        }
        void access_priv(StaticNested o) {
            int priv_field = o.priv_field++;
        }
    }

    public static void main(String[] args) {
        TestPrivateStaticField o = new TestPrivateStaticField();
        StaticNested s = new StaticNested();
        InnerNested i = o.new InnerNested();
        StaticIface intf = new StaticIface() {};

        o.access_priv(new TestPrivateStaticField());
        o.access_priv(s);

        s.access_priv(o);
        s.access_priv(new StaticNested());

        i.access_priv(o);
        i.access_priv(s);

        intf.access_priv(o);
        intf.access_priv(s);
    }
}
