/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

// C extends D in first class loader
public class C extends D {
  static { System.out.println("C called"); }
  public C() { System.out.println("C.<init> called"); }
}

