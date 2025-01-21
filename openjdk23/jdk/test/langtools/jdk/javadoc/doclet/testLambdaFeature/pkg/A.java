/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package pkg;

@FunctionalInterface
public interface A {

    public void method1();

    public default void defaultMethod() { }
}
