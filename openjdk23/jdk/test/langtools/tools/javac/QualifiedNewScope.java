/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4406269
 * @summary incorrect scoping for type name in qualified new expression.
 * @author gafter
 *
 * @compile QualifiedNewScope.java
 */

package qualifiedNewScope;

class A {
    class B {
    }
    public static void main(String[] args) {
        new A(){}.new B(){};
    }
}
