/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

interface A {
   default String u() { return "A"; }
   default String name() {
      SAM s = ()->u()+"A";
      return s.m();
   }
}
