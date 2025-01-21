/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4865660 4906400
 * @summary implement "metadata" (attribute interfaces and program annotations)
 * @author gafter
 *
 * @compile Z3.java
 */

enum Color { red, green, blue }

class X {}

@interface An2 {}

@interface An {
    int a();
    float b();
    double c();
    String d();
    Color e();
    Class f();
    Class<?> h();
    Class<? extends Enum> i();
    int[] j();
    // int[][] k();
    An2 l();
    // X m();
}
