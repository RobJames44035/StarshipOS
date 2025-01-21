/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

interface C {
   default String u() { return "C"; }
   default String name() {
      SAM s = ()->u()+"C";
      return s.m();
   }
}
