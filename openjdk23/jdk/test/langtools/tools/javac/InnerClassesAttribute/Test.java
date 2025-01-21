/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4070745
 * @summary The compiler used to crash when it saw a correctly-formed
 *          InnerClasses attribute in a .class file.
 * @author turnidge
 * @build Outside Outside$1$Inside
 * @compile Test.java
 */
public
class Test {
    Outside x = null;
}
