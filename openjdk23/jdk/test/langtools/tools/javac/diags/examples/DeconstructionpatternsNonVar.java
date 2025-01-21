/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

// key: compiler.err.deconstruction.pattern.var.not.allowed

class DeconstructionpatternsNonVar {
    record Point(int x, int y) {}
    enum Color {RED, GREEN, BLUE}
    record ColoredPoint (Point p, Color c) {}

    public static void foo(Object o) {
        if (o instanceof ColoredPoint(var(var x, var y), var c)) {
        }
    }
}
