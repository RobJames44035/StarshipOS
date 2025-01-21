/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4738690
 * @summary javac improperly complains of conflict with non-inherited method
 * @author gafter
 *
 * @compile A.java B.java
 */

package a;
public abstract class A {
    abstract void f();
}
