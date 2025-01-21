/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

enum NoFinal {
    A {
        protected void finalize() {
            System.err.println("FISK");
        }
    };
}
