/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class MethodAmbiguityCrash2 {

    public interface A {
        int op();
    }

    public abstract static class B {
        public abstract int op();
    }

    public abstract static class C extends B implements A {

        public C(int x) {
        }

        public C() {
            this(op());     // compile should fail here
        }
    }
}
