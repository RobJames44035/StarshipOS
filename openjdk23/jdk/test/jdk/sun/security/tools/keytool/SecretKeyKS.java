/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4694076
 * @summary KeyTool throws ArrayIndexOutOfBoundsException for listing
 *          SecretKey entries in non-verbose mode.
 * @author Valerie Peng
 * @library /test/lib
 */

import jdk.test.lib.SecurityTools;

import java.nio.file.Path;

public class SecretKeyKS {
    public static void main(String[] args) throws Exception {
        SecurityTools.keytool("-list -keystore " +
                Path.of(System.getProperty("test.src"), "SecretKeyKS.jks") +
                " -storepass password").shouldHaveExitValue(0);
    }
}
