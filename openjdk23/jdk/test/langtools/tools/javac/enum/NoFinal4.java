/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

enum NoFinal4 {
    A, B, C;
    private void finalize() {
        System.err.println("FISK");
    }
}
