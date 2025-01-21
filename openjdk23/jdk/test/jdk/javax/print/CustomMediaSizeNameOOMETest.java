/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 7001133
 * @summary OutOfMemoryError by CustomMediaSizeName implementation
 * @run main CustomMediaSizeNameOOMETest
 * @run main/timeout=300/othervm -Xmx8m CustomMediaSizeNameOOMETest
*/

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class CustomMediaSizeNameOOMETest {

    private static final int MILLIS = 3000;

    public static void main(String[] args) {

        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if (services == null || services.length == 0) {
            return;
        }

        for (PrintService service : services) {
            service.getUnsupportedAttributes(null, null);
        }

        long time = System.currentTimeMillis() + MILLIS;

        do {
            for (int i = 0; i < 2000; i++) {
                for (PrintService service : services) {
                    service.getAttributes();
                }
            }
        } while (time > System.currentTimeMillis());
    }
}
