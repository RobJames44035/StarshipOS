/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5094318
 * @summary REGRESSION: Array cloning is not backwards compatible
 *
 * @compile  T5094318.java
 * @run main/fail T5094318
 */

public class T5094318 {

    // Tiger
    public static void method(int[] array){
        System.out.println("You gave me an array of ints");
        throw new RuntimeException();
    }

    // Pre-Tiger
    public static void method(Object obj){
        System.out.println("You gave me an object");
    }

    public static void main(String[] args){
        method(new int[0].clone());
    }

}
