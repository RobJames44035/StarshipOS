/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8046171
 * @summary Test access to private fields between nestmates and nest-host
 *          using different flavours of named nested types
 * @run main TestPrivateField
 */

public class TestPrivateField {

    // Private field of nest-host for nestmates to access
    private int priv_field;

    // public constructor so we aren't relying on private access
    public TestPrivateField() {}

    // Methods that will access private fields of nestmates

    // NOTE: No StaticIface variants as interfaces can't have instance fields

    void access_priv(TestPrivateField o) {
        this.priv_field = o.priv_field++;
    }
    void access_priv(InnerNested o) {
        this.priv_field = o.priv_field++;
    }
    void access_priv(StaticNested o) {
        this.priv_field = o.priv_field++;
    }

    // The various nestmates

    static interface StaticIface {

        // Methods that will access private fields of nestmates

        default void access_priv(TestPrivateField o) {
            int priv_field = o.priv_field++;
        }
        default void access_priv(InnerNested o) {
            int priv_field = o.priv_field++;
        }
        default void access_priv(StaticNested o) {
            int priv_field = o.priv_field++;
        }
    }

    static class StaticNested {

        private int priv_field;

        // public constructor so we aren't relying on private access
        public StaticNested() {}

        // Methods that will access private fields of nestmates

        void access_priv(TestPrivateField o) {
            this.priv_field = o.priv_field++;
        }
        void access_priv(InnerNested o) {
            this.priv_field = o.priv_field++;
        }
        void access_priv(StaticNested o) {
            this.priv_field = o.priv_field++;
        }
    }

    class InnerNested {

        private int priv_field;

        // public constructor so we aren't relying on private access
        public InnerNested() {}

        void access_priv(TestPrivateField o) {
            this.priv_field = o.priv_field++;
        }
        void access_priv(InnerNested o) {
            this.priv_field = o.priv_field++;
        }
        void access_priv(StaticNested o) {
            this.priv_field = o.priv_field++;
        }
    }

    public static void main(String[] args) {
        TestPrivateField o = new TestPrivateField();
        StaticNested s = new StaticNested();
        InnerNested i = o.new InnerNested();
        StaticIface intf = new StaticIface() {};

        o.access_priv(new TestPrivateField());
        o.access_priv(i);
        o.access_priv(s);

        s.access_priv(o);
        s.access_priv(i);
        s.access_priv(new StaticNested());

        i.access_priv(o);
        i.access_priv(o.new InnerNested());
        i.access_priv(s);

        intf.access_priv(o);
        intf.access_priv(i);
        intf.access_priv(s);
    }
}
