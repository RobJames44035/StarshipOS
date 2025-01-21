/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8305582
 * @summary Compiler crash when compiling record patterns with var
 * @compile/fail/ref=T8305582.out -XDrawDiagnostics -XDshould-stop.at=FLOW T8305582.java
 */

public class T8305582 {
    record Point(int x, int y) {}
    enum Color {RED, GREEN, BLUE}
    record ColoredPoint (Point p, Color c) {}

    public static void foo(Object o) {
        if (o instanceof ColoredPoint(var(var x, var y), var c)) { }

        switch(o) {
            case ColoredPoint(var (var x, var y), var c):
                break;
            default:
        }

        if (o instanceof ColoredPoint(var(int x, int y), var c)) { }
        if (o instanceof ColoredPoint(var(int x, var y), var c)) { }
        if (o instanceof var(Point x, Point y)) { }
    }
}
