/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
interface I{
  int f();
}

class C {
    public static int f() {
        return 7;
    }
}

class StaticOverride extends C implements I { }
