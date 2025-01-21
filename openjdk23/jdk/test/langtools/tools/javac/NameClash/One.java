/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4090536
 * @summary The class One exists in package a and package b.  The compiler
 *          used to report this as an ambiguity.  It should not.
 * @author turnidge
 *
 * @compile One.java
 */

import a.*;
import b.*;

public class One {
}
