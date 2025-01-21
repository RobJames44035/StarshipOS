/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.possible.fall-through.into.case
// options: -Xlint:fallthrough

class PossibleFallThrough {
    void m(int i) {
        switch (i) {
            case 0:
                System.out.println(0);
            case 1:
                System.out.println(1);
        }
    }
}

