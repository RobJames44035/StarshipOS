/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4081024 4662489
 * @summary Verify that clash is allowed between class declaration and import of same class.
 * @author maddox, gafter
 *
 * @compile ImportOfOwnClass.java
 */

package p;

import p.ImportOfOwnClass;

public class ImportOfOwnClass {  // name clashes with import
    ImportOfOwnClass() {}
}
