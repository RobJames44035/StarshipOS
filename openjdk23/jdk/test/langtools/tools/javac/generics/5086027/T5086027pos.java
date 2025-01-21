/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     5086027
 * @summary Inner class of generic class cannot extend Throwable
 * @author  Peter von der Ah\u00e9
 * @compile T5086027pos.java
 */

public class T5086027pos<T> {
    static class X extends Exception { }
}
