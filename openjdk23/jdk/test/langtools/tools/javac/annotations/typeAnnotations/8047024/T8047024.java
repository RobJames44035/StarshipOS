/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8047024
 * @summary AssertionError: exception_index already contains a bytecode offset
 * @compile T8047024_01.java
 * @compile -parameters T8047024.java
 */

public class T8047024 {
    public static void main(String [] args) {
        T8047024_01.run();
    }
}
