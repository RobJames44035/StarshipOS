/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

enum NoFinal2 {
    A, B, C;
    protected void finalize() {
        System.err.println("FISK");
    }
}
