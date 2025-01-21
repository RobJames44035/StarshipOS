/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package java;

public class InProhibitedPkg {
    static {
        if (true) {
            throw new RuntimeException("This class shouldn't be loaded by any loader other than BOOT");
        }
    }
}
