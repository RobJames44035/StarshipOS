/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8046171
 * @summary Test JNI access to private methods between nestmates and nest-host
 *          using different flavours of named nested types
 * @compile ../NestmatesJNI.java
 * @run main/othervm/native TestJNI
 * @run main/othervm/native -Xcheck:jni TestJNI
 */
public class TestJNI {

    // Unlike reflection, the calling context is not relevant to JNI
    // calls, but we keep the same structure as the reflection tests.

    static final String METHOD = "priv_invoke";

    // Private method of nest-host for nestmates to access
    private void priv_invoke() {
        System.out.println("TestJNI::priv_invoke");
    }

    // public constructor so we aren't relying on private access
    public TestJNI() {}

    // Methods that will access private methods of nestmates

    void access_priv(TestJNI o) {
        doCall(o, o.getClass(), METHOD, true);
        doCall(o, o.getClass(), METHOD, false);
    }
    void access_priv(InnerNested o) {
        doCall(o, o.getClass(), METHOD, true);
        doCall(o, o.getClass(), METHOD, false);
    }
    void access_priv(StaticNested o) {
        doCall(o, o.getClass(), METHOD, true);
        doCall(o, o.getClass(), METHOD, false);
    }
    void access_priv(StaticIface o) {
        // Can't use o.getClass() as the method is not in that class
        doCall(o, StaticIface.class, METHOD, true);
        doCall(o, StaticIface.class, METHOD, false);
    }

    // The various nestmates

    static interface StaticIface {

        private void priv_invoke() {
            System.out.println("StaticIface::priv_invoke");
        }

        // Methods that will access private methods of nestmates

        default void access_priv(TestJNI o) {
            doCall(o, o.getClass(), METHOD, true);
            doCall(o, o.getClass(), METHOD, false);
        }
        default void access_priv(InnerNested o) {
            doCall(o, o.getClass(), METHOD, true);
            doCall(o, o.getClass(), METHOD, false);
        }
        default void access_priv(StaticNested o) {
            doCall(o, o.getClass(), METHOD, true);
            doCall(o, o.getClass(), METHOD, false);
        }
        default void access_priv(StaticIface o) {
            // Can't use o.getClass() as the method is not in that class
            doCall(o, StaticIface.class, METHOD, true);
            doCall(o, StaticIface.class, METHOD, false);
        }
    }

    static class StaticNested {

        private void priv_invoke() {
            System.out.println("StaticNested::priv_invoke");
        }

        // public constructor so we aren't relying on private access
        public StaticNested() {}

        // Methods that will access private methods of nestmates

        void access_priv(TestJNI o) {
            doCall(o, o.getClass(), METHOD, true);
            doCall(o, o.getClass(), METHOD, false);
        }
        void access_priv(InnerNested o) {
            doCall(o, o.getClass(), METHOD, true);
            doCall(o, o.getClass(), METHOD, false);
        }
        void access_priv(StaticNested o) {
            doCall(o, o.getClass(), METHOD, true);
            doCall(o, o.getClass(), METHOD, false);
        }
        void access_priv(StaticIface o) {
            // Can't use o.getClass() as the method is not in that class
            doCall(o, StaticIface.class, METHOD, true);
            doCall(o, StaticIface.class, METHOD, false);
        }
    }

    class InnerNested {

        private void priv_invoke() {
            System.out.println("InnerNested::priv_invoke");
        }

        // public constructor so we aren't relying on private access
        public InnerNested() {}

        void access_priv(TestJNI o) {
            doCall(o, o.getClass(), METHOD, true);
            doCall(o, o.getClass(), METHOD, false);
        }
        void access_priv(InnerNested o) {
            doCall(o, o.getClass(), METHOD, true);
            doCall(o, o.getClass(), METHOD, false);
        }
        void access_priv(StaticNested o) {
            doCall(o, o.getClass(), METHOD, true);
            doCall(o, o.getClass(), METHOD, false);
        }
        void access_priv(StaticIface o) {
            // Can't use o.getClass() as the method is not in that class
            doCall(o, StaticIface.class, METHOD, true);
            doCall(o, StaticIface.class, METHOD, false);
        }
    }

    public static void main(String[] args) {
        TestJNI o = new TestJNI();
        StaticNested s = new StaticNested();
        InnerNested i = o.new InnerNested();
        StaticIface intf = new StaticIface() {};

        o.access_priv(new TestJNI());
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


    static void doCall(Object target, Class<?> klass, String method,
                       boolean virtual) {
        String definingClass = klass.getName();
        String desc = (virtual ? "Virtual" : "Nonvirtual") + " Invocation of " +
                       definingClass + "." + method + " on instance of class " +
                       target.getClass().getName();
        try {
            NestmatesJNI.callVoidVoid(target, definingClass, method, virtual);
            System.out.println(desc + " - passed");
        }
        catch (Throwable t) {
            throw new Error(desc + ": Unexpected exception: " + t, t);
        }
    }
}
