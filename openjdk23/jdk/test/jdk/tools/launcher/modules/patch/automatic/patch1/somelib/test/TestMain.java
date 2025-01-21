/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package somelib.test;

import somelib.Invariants;

/**
 * This test is modelled to use --patch-module to gain access to non-exported internals.
 */

public class TestMain {
    public static void main(String[] args) {
        Invariants.test("patch1");
    }
}
