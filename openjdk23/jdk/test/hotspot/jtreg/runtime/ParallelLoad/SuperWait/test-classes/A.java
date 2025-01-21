/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

// A extends B in other Class loader.
public class A extends B {
  static { System.out.println("A called"); }
  public A() { System.out.println("A.<init> called"); }
}

