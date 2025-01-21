/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8046171
 * @summary Test access to private constructors between nestmates and nest-host
 *          using different flavours of named nested types that will
 *          generate invokespecial for the calls. The -Xcomp run is a special
 *          regression test for a compiler assertion that would fire when
 *          "loading" a nest-host class.
 * @run main TestInvokeSpecial
 * @run main/othervm -Xcomp TestInvokeSpecial
 */

public class TestInvokeSpecial {

    // All constructors are private to ensure nestmate access checks apply

    // All doConstruct methods are public so they don't involve invoke_special

    private TestInvokeSpecial() {}

    // The various nestmates

    static interface StaticIface {

        // Methods that will access private constructors of nestmates.
        // The arg is a dummy for overloading purposes

        default void doConstruct(TestInvokeSpecial o) {
            Object obj = new TestInvokeSpecial();
        }
        default void doConstruct(InnerNested o) {
            Object obj = new TestInvokeSpecial().new InnerNested();
        }
        default void doConstruct(StaticNested o) {
            Object obj = new StaticNested();
        }
        default void doConstruct(StaticIface o) {
            Object obj = new StaticIface() {};
        }
    }

    static class StaticNested {

        private StaticNested() {}

        // Methods that will access private constructors of nestmates.
        // The arg is a dummy for overloading purposes

        public void doConstruct(TestInvokeSpecial o) {
            Object obj = new TestInvokeSpecial();
        }
        public void doConstruct(InnerNested o) {
            Object obj = new TestInvokeSpecial().new InnerNested();
        }
        public void doConstruct(StaticNested o) {
            Object obj = new StaticNested();
        }
        public void doConstruct(StaticIface o) {
            Object obj = new StaticIface() {};
        }
    }

    class InnerNested {

        private InnerNested() {}

        // Methods that will access private constructors of nestmates.
        // The arg is a dummy for overloading purposes

        public void doConstruct(TestInvokeSpecial o) {
            Object obj = new TestInvokeSpecial();
        }
        public void doConstruct(InnerNested o) {
            Object obj = new TestInvokeSpecial().new InnerNested();
        }
        public void doConstruct(StaticNested o) {
            Object obj = new StaticNested();
        }
        public void doConstruct(StaticIface o) {
            Object obj = new StaticIface() {};
        }
    }

    public static void main(String[] args) {
        // These initial constructions test nest-host access
        TestInvokeSpecial o = new TestInvokeSpecial();
        StaticNested s = new StaticNested();
        InnerNested i = o.new InnerNested();
        StaticIface intf = new StaticIface() {};

        s.doConstruct(o);
        s.doConstruct(i);
        s.doConstruct(s);
        s.doConstruct(intf);

        i.doConstruct(o);
        i.doConstruct(i);
        i.doConstruct(s);
        i.doConstruct(intf);

        intf.doConstruct(o);
        intf.doConstruct(i);
        intf.doConstruct(s);
        intf.doConstruct(intf);
    }
}
