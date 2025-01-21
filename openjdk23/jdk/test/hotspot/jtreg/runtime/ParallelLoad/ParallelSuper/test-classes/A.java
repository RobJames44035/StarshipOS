/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

public class A extends B {
    public A() {
       System.out.println("A extends B");
       throw new RuntimeException("Should throw CCE here");
    }
}
