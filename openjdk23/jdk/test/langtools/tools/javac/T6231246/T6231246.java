/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6231246
 * @summary Javac crash with -g:none
 * @author  Peter von der Ah\u00e9
 * @compile -g:none T6231246.java
 */

public class T6231246 {
    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}
