/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class BadTwr implements AutoCloseable {
    public static void meth(String... args) {
        // illegal repeated name
        try(BadTwr r1 = new BadTwr(); BadTwr r1 = new BadTwr()) {
            System.out.println(r1.toString());
        }

        // illegal duplicate name of method argument
        try(BadTwr args = new BadTwr()) {
            System.out.println(args.toString());
            final BadTwr thatsIt = new BadTwr();
            thatsIt = null;
        }

        try(BadTwr name = new BadTwr()) {
            // illegal duplicate name of enclosing try
            try(BadTwr name = new BadTwr()) {
                System.out.println(name.toString());
            }
        }

    }

    public void close() {
        ;
    }
}
