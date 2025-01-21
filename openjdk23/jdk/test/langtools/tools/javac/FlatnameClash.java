/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4302890
 * @summary Verify that clash between user-defined class and
 * flatname of member class is detected.
 * @author William Maddox
 *
 * @run compile/fail FlatnameClash.java
 */

class FlatnameClash$Inner {}

public class FlatnameClash {
    class Inner { }   // error
}
