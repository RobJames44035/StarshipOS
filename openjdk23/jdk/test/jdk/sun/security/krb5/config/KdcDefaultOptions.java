/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
/*
 * @test
 * @bug 7195426
 * @summary kdc_default_options not supported correctly
 * @modules java.security.jgss/sun.security.krb5
 *          java.security.jgss/sun.security.krb5.internal
 * @compile -XDignore.symbol.file KdcDefaultOptions.java
 * @run main/othervm KdcDefaultOptions
 */

import sun.security.krb5.Config;
import sun.security.krb5.internal.KDCOptions;

public class KdcDefaultOptions {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.security.krb5.conf",
                System.getProperty("test.src", ".") + "/kdc_default_options.conf");
        Config.refresh();
        KDCOptions options = new KDCOptions();
        if (!options.get(KDCOptions.FORWARDABLE) ||
                !options.get(KDCOptions.PROXIABLE) ||
                !options.get(KDCOptions.RENEWABLE_OK)) {
            throw new Exception(options.toString());
        }
    }
}
