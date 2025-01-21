/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package pkg;

/**
 * TestClass declaration.
 * @since 1.2
 */
public class TestClass {

    /**
     * TestClass field.
     * @since 1.2
     */
    public int field;

    /**
     * TestClass constructor.
     * @since 2.0b
     */
    @Deprecated(since="6")
    public TestClass() {}

    /**
     * TestClass constructor.
     * @since 3.2
     */
    public TestClass(String s) {}

    /**
     * TestClass method.
     * @since 2.0b
     */
    public void method() {}

    /**
     * TestClass overloaded method.
     * @since 5
     */
    public void overloadedMethod(String s) {}

    /**
     * TestClass overloaded method.
     * @since 6
     */
    public void overloadedMethod(int i) {}
}
