/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/**
 * @test
 * @bug 6763518
 * @summary Impossible to suppress raw-type warnings
 * @compile -Werror -Xlint:rawtypes T6763518.java
 */

import java.util.List;

class T6763518 {
    @SuppressWarnings("rawtypes")
    List l1;

    void m(@SuppressWarnings("rawtypes") List l2) {
        @SuppressWarnings("rawtypes")
        List l3;
    };
}
