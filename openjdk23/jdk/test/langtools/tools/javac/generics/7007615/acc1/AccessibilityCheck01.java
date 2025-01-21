/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class AccessibilityCheck01 extends p2.E {
  String m(Object o) { return "hi"; } // this is okay
  int m(String s) { return 3; } // this overrides m(String) illegally
}
