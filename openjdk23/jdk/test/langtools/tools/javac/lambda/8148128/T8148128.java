/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8148128
 * @summary Regression: array constructor references marked as inexact
 * @compile T8148128.java
 */
import java.util.*;
import java.util.stream.*;

class T8148128 {
    public static void doSomething (List<String>[] stuff) {
        System.out.println("List Stuff");
    }

    public static void doSomething (Set<String>[] stuff) {
        System.out.println("Set Stuff");
    }

    public static void main (String[] args) {
        doSomething(Stream.of("Foo", "Bar").map(Collections::singletonList).toArray(List[]::new));
    }
}
