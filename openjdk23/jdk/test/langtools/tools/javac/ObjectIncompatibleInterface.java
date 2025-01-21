/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4479715
 * @summary compiler incorrectly rejects interface int clone();
 * @author gafter
 *
 * @compile ObjectIncompatibleInterface.java
 */

/** While it's impossible to implement this interface, nevertheless
 *  JLS requires it to be accepted.
 */
interface ObjectIncompatibleInterface {
    int clone();
}
