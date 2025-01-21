/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.types.correctness.hierarchies;

public class OneRank {
    private OneRank() {
    }

    public static class Hierarchy extends TypeHierarchy<TypeHierarchy.A, OneRank.B> {
        public Hierarchy() {
            super(new TypeHierarchy.A(), new OneRank.B(),
                    TypeHierarchy.A.class, OneRank.B.class);
        }
    }

    public static class B implements TypeHierarchy.I {
        @Override
        public int m() {
            return TypeHierarchy.YEAR;
        }
    }

}
