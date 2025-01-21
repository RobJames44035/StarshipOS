/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * This class tests to see if System.in.available starts
 * with an appropriate value
 *
 * @test
 * @bug 4104888
 * @summary Test for System.in.available
 */

public class Available {
    public static void main(String args[]) throws Exception {
        int bytesAvailable = System.in.available();
        if (bytesAvailable != 0)
            throw new RuntimeException("System.in.available returned non-zero");
        }
}
