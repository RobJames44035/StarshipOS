/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @summary Verify that definite assignment works (legal code)
 * @compile DefiniteAssignment1.java
 * @run main DefiniteAssignment1
 */
public class DefiniteAssignment1 {
    public static void main(String[] args) {
        int a = 0;

        {
        int x;

        switch(a) {
            case 0: x = 0; break;
            default: x = 1; break;
        }

        if (x != 0)
            throw new IllegalStateException("Unexpected value.");
        }

        {
        int x;

        switch(a) {
            case 1: x = 1; break;
            case 0:
            default: x = 0; break;
        }

        if (x != 0)
            throw new IllegalStateException("Unexpected value.");
        }

        {
        int x;

        switch(a) {
            case 1: x = 1; break;
            case 0:
            default: x = 0;
        }

        if (x != 0)
            throw new IllegalStateException("Unexpected value.");
        }

        {
        int x;

        switch(a) {
            case 0 -> x = 0;
            default -> x = 1;
        }

        if (x != 0)
            throw new IllegalStateException("Unexpected value.");
        }

        {
        int x;

        try {
            switch(a) {
                case 1: x = 1; break;
                case 0:
                default: throw new UnsupportedOperationException();
            }

            throw new IllegalStateException("Unexpected value: " + x);
            } catch (UnsupportedOperationException ex) {
                //OK
            }
        }

        {
        int x;

        switch(a) {
            case 0 -> x = 0;
            default -> throw new IllegalStateException();
        }

        if (x != 0)
            throw new IllegalStateException("Unexpected value.");
        }
    }

    enum E {
        A, B, C;
    }
}
