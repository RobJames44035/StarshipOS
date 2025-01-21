/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class TestCrashNestedAnnos {
    // A and B are not annotation types
    @A(@A1()) int foo() {}
    @B(@B1()) int bar() {}
}

class B {}
class B1 {}
