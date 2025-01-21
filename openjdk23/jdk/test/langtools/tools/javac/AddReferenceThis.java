/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4062064
 * @summary Implicit casts from int to char used to screw up the internal
 *          `this' object in a bad way.
 * @author turnidge
 *
 * @compile AddReferenceThis.java
 */

public
class AddReferenceThis
{
    final int CR = '\r';
    final int LF = '\n';
    final char[] CRLF = { CR, LF};
}
