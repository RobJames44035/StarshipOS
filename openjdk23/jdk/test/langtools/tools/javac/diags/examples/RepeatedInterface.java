/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.repeated.interface

interface I { }

class RepeatedInterface
  implements I, I
{ }
