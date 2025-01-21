/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8046171
 * @summary Test access to private methods between nestmates and nest-host
 *          using different flavours of named nested types
  * @run main TestInvoke
 */

public class TestInvoke {

    // Private method of nest-host for nestmates to access
    private void priv_invoke() {
        System.out.println("TestInvoke::priv_invoke");
    }

    // public constructor so we aren't relying on private access
    public TestInvoke() {}

    // Methods that will access private methods of nestmates

    void access_priv(TestInvoke o) {
        o.priv_invoke();
    }
    void access_priv(InnerNested o) {
        o.priv_invoke();
    }
    void access_priv(StaticNested o) {
        o.priv_invoke();
    }
    void access_priv(StaticIface o) {
        o.priv_invoke();
    }

    // The various nestmates

    static interface StaticIface {

        private void priv_invoke() {
            System.out.println("StaticIface::priv_invoke");
        }

        // Methods that will access private methods of nestmates

        default void access_priv(TestInvoke o) {
            o.priv_invoke();
        }
        default void access_priv(InnerNested o) {
            o.priv_invoke();
        }
        default void access_priv(StaticNested o) {
            o.priv_invoke();
        }
        default void access_priv(StaticIface o) {
            o.priv_invoke();
        }
    }

    static class StaticNested {

        private void priv_invoke() {
            System.out.println("StaticNested::priv_invoke");
        }

        // public constructor so we aren't relying on private access
        public StaticNested() {}

        // Methods that will access private methods of nestmates

        void access_priv(TestInvoke o) {
            o.priv_invoke();
        }
        void access_priv(InnerNested o) {
            o.priv_invoke();
        }
        void access_priv(StaticNested o) {
            o.priv_invoke();
        }
        void access_priv(StaticIface o) {
            o.priv_invoke();
        }
    }

    class InnerNested {

        private void priv_invoke() {
            System.out.println("InnerNested::priv_invoke");
        }

        // public constructor so we aren't relying on private access
        public InnerNested() {}

        void access_priv() {
            TestInvoke.this.priv_invoke(); // check this$0 access
        }
        void access_priv(TestInvoke o) {
            o.priv_invoke();
        }
        void access_priv(InnerNested o) {
            o.priv_invoke();
        }
        void access_priv(StaticNested o) {
            o.priv_invoke();
        }
        void access_priv(StaticIface o) {
            o.priv_invoke();
        }
    }

    public static void main(String[] args) {
        TestInvoke o = new TestInvoke();
        StaticNested s = new StaticNested();
        InnerNested i = o.new InnerNested();
        StaticIface intf = new StaticIface() {};

        o.access_priv(new TestInvoke());
        o.access_priv(i);
        o.access_priv(s);
        o.access_priv(intf);

        s.access_priv(o);
        s.access_priv(i);
        s.access_priv(new StaticNested());
        s.access_priv(intf);

        i.access_priv();
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
