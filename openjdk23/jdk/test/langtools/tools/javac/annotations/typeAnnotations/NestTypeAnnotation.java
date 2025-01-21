/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8274942
 * @summary javac should attribute the internal annotations of the annotation element value
 * @compile NestTypeAnnotation.java
 */

import java.lang.annotation.*;

public class NestTypeAnnotation {
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    public @interface OuterAnnotation {
        int intVal();
        float floatVal();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    public @interface InnerAnnotation { }

    public static void main(String[] args) {
        int intVal1 = (@OuterAnnotation(intVal = (@InnerAnnotation() int) 2.5, floatVal = (@InnerAnnotation() float) 2.5) int) 2.4;
        int[] arr = new int []{1, 2}; // use `2.4 * arr[0] + arr[1]` to prevent optimization.
        int intVal2 = (@OuterAnnotation(intVal = (@InnerAnnotation() int) 2.5, floatVal = (@InnerAnnotation() float) 2.5) int) (2.4 * arr[0] + arr[1]);

        int[] singleArr1 = new @OuterAnnotation(intVal = (@InnerAnnotation() int) 2.5, floatVal = (@InnerAnnotation() float) 2.5) int [2];
        @OuterAnnotation(intVal = (@InnerAnnotation() int) 2.5, floatVal = (@InnerAnnotation() float) 2.5) int[] singleArr2 = new int [2];
        int[] singleArr3 = new  int @OuterAnnotation(intVal = (@InnerAnnotation() int) 2.5, floatVal = (@InnerAnnotation() float) 2.5) [2];

        int[][] multiArr1 = new int @OuterAnnotation(intVal = (@InnerAnnotation() int) 2.5, floatVal = (@InnerAnnotation() float) 2.5) [2][3];
        int[][] multiArr2 = new int [2] @OuterAnnotation(intVal = (@InnerAnnotation() int) 2.5, floatVal = (@InnerAnnotation() float) 2.5) [3];
    }
}
