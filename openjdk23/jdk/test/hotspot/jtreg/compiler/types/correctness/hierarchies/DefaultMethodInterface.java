/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.types.correctness.hierarchies;

public class DefaultMethodInterface {
    private DefaultMethodInterface() {
    }

    public static class Hierarchy
            extends TypeHierarchy<DefaultMethodInterface.A, DefaultMethodInterface.B> {
        public Hierarchy() {
            super(new DefaultMethodInterface.A(), new DefaultMethodInterface.B(),
                    DefaultMethodInterface.A.class, DefaultMethodInterface.B.class);
        }
    }

    public static interface I2 extends TypeHierarchy.I {
        default int m() {
            return TypeHierarchy.ANSWER;
        }
    }

    public static class A implements I2 {
        // use default method from I2
    }

    public static class B extends A {
        @Override
        public int m() {
            return TypeHierarchy.YEAR;
        }
    }
}
