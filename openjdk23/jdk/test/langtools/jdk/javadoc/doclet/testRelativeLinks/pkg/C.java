/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

/**
 * Here are two relative links in a class:
 * <a href="relative-class-link.html">relative class link</a>,
 * <a href="#class-fragment">fragment class link</a>.
 *
 * <a id="class-fragment">Class fragment</a>.
 */
public class C {

    /**
     * Here is a relative link in a field:\u0130
     * <a href="relative-field-link.html">relative field link</a>.
     */
    public C field = null;

    /**
     * Here are two relative links in a method:
     * <a href="relative-method-link.html">relative method link</a>,
     * <a href="#method-fragment">fragment method link</a>.
     */
    public C method() { return null;}

    /**
     * Here is a relative link in a method:
     * <a
     * href="relative-multi-line-link.html">relative-multi-line-link</a>.
     *
     * <a id="method-fragment">Method fragment</a>.
     */
    public C multipleLineTest() { return null;}

    /**
     * <a id="masters"></a>
     * Something that goes holy cow. Second line.
     */
    public static class WithAnAnchor{}

}
