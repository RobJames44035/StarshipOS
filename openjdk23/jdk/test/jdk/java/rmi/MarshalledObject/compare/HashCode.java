/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4105900
 * @summary MarshalledObject with null throws NullPointerException
 * @author Ken Arnold
 *
 * @run main/othervm HashCode 11 annotatedRef
 */

import java.rmi.MarshalledObject;

public class HashCode {
    public static void main(String[] args) throws Throwable {
        Compare.compareHashCodes(args);
    }
}
