/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8186961
 * @run main/othervm StaticFieldsOnInterface C
 * @run main/othervm StaticFieldsOnInterface D
 * @run main/othervm StaticFieldsOnInterface Y
 */

public class StaticFieldsOnInterface {
    /*
            A
           / \
          B  C
          \  /
           D

        Interface A has a public field
        Ensure B, C, D only report exactly one public field

          A
         /
        X A
        |/
        Y

        Ensure class Y, extending class X, reports exactly one public field
     */

    public interface A {
        public static final int CONSTANT = 42;
    }

    public interface B extends A {
    }

    public interface C extends A {
    }

    public interface D extends B, C {
    }

    static class X implements A {}
    static class Y extends X implements A {}

    public static void main(String[] args) throws Exception {
        char first = 'C';
        if (args.length > 0) {
            first = args[0].charAt(0);
        }

        assertOneField(A.class);
        // D first
        if (first == 'D') {
            assertOneField(D.class);
            assertOneField(C.class);
        }
        // C first
        else if (first == 'C') {
            assertOneField(C.class);
            assertOneField(D.class);
        }
        else {
            assertOneField(Y.class);
        }
    }

    static void assertOneField(Class<?> c) {
        int nfs = c.getFields().length;
        if (nfs != 1) {
            throw new AssertionError(String.format(
                    "Class %s does not have exactly one field: %d", c.getName(), nfs));
        }
    }
}
