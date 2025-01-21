/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test
 * @bug 6857795 8075299
 * @summary Checks if kinit uses both krb5 conf file and system properties
 * @requires os.family == "windows"
 * @library /test/lib
 * @library /sun/security/krb5/auto
 * @run main/othervm KinitConfPlusProps
 */
public class KinitConfPlusProps {

    private static final String KINIT = System.getProperty("java.home")
            + File.separator + "bin" + File.separator + "kinit";
    private static final String KLIST = System.getProperty("java.home")
            + File.separator + "bin" + File.separator + "klist";
    private static final String REALM = "REALM";
    private static final String ANOTHER_REALM = "ANOTHER.REALM";
    private static final String HOST = "localhost";
    private static final String CC_FILENAME = "krb5cc_test";
    private static final String USER = "TESTER";
    private static final String USER_PRINCIPAL = USER + "@" + REALM;
    private static final String KRBTGT_PRINCIPAL = "krbtgt/" + REALM;
    private static final String KEYTAB_FILE = "test.keytab";
    private static final String KRB5_CONF_FILENAME = "krb5.conf";

    public static void main(String[] args) throws Exception {
        // define principals
        Map<String, String> principals = new HashMap<>();
        principals.put(USER_PRINCIPAL, null);
        principals.put(KRBTGT_PRINCIPAL, null);

        System.setProperty("java.security.krb5.conf", KRB5_CONF_FILENAME);

        // start a local KDC instance
        KDC kdc = KDC.startKDC(HOST, null, REALM, principals, KEYTAB_FILE,
                KDC.KtabMode.APPEND);
        KDC.saveConfig(KRB5_CONF_FILENAME, kdc,
                "forwardable = true", "proxiable = true");

        boolean success = true;

        /*
         * kinit should fail since java.security.krb5.kdc
         * and java.security.krb5.realm properties override correct values
         * in krb5 conf file
         */
        String[] command = {KINIT, "-k",
            "-J-Djava.security.krb5.realm=" + REALM,
            "-J-Djava.security.krb5.kdc=" + HOST,   // without port
            "-J-Djava.security.krb5.conf=" + KRB5_CONF_FILENAME,
            "-t", KEYTAB_FILE,
            "-c", CC_FILENAME,
            USER
        };

        try {
            OutputAnalyzer out = ProcessTools.executeCommand(command);
            out.shouldHaveExitValue(-1);
        } catch(Throwable e) {
            System.out.println("Unexpected exception: " + e);
            e.printStackTrace(System.out);
            success = false;
        }

        /*
         * kinit should succeed
         * since realm should be picked up from principal name
         */
        command = new String[] {KINIT, "-k",
            "-J-Djava.security.krb5.realm=" + ANOTHER_REALM,
            "-J-Djava.security.krb5.kdc=" + HOST,
            "-J-Djava.security.krb5.conf=" + KRB5_CONF_FILENAME,
            "-t", KEYTAB_FILE,
            "-c", CC_FILENAME,
            USER_PRINCIPAL
        };

        try {
            OutputAnalyzer out = ProcessTools.executeCommand(command);
            out.shouldHaveExitValue(0);
            out.shouldContain(CC_FILENAME);
        } catch(Throwable e) {
            System.out.println("Unexpected exception: " + e);
            e.printStackTrace(System.out);
            success = false;
        }

        success &= checkTicketFlags();

        /*
         * kinit should succeed
         * since realm should be picked up from principal name,
         * and other data should come from krb5 conf file
         */
        command = new String[] {KINIT, "-k",
            "-J-Djava.security.krb5.conf=" + KRB5_CONF_FILENAME,
            "-t", KEYTAB_FILE,
            "-c", CC_FILENAME,
            USER_PRINCIPAL
        };

        try {
            OutputAnalyzer out = ProcessTools.executeCommand(command);
            out.shouldHaveExitValue(0);
            out.shouldContain(CC_FILENAME);
        } catch(Throwable e) {
            System.out.println("Unexpected exception: " + e);
            e.printStackTrace(System.out);
            success = false;
        }

        success &= checkTicketFlags();

        // kinit should succeed even if a principal name doesn't have realm
        command = new String[] {KINIT, "-k",
            "-J-Djava.security.krb5.conf=" + KRB5_CONF_FILENAME,
            "-t", KEYTAB_FILE,
            "-c", CC_FILENAME,
            USER
        };

        try {
            OutputAnalyzer out = ProcessTools.executeCommand(command);
            out.shouldHaveExitValue(0);
            out.shouldContain(CC_FILENAME);
        } catch(Throwable e) {
            System.out.println("Unexpected exception: " + e);
            e.printStackTrace(System.out);
            success = false;
        }

        success &= checkTicketFlags();

        if (!success) {
            throw new RuntimeException("At least one test case failed");
        }
        System.out.println("Test passed");
    }

    // check if a ticket has forwardable and proxiable flags
    private static boolean checkTicketFlags() {
        String[] command = new String[] {KLIST, "-f", "-c", CC_FILENAME};

        try {
            OutputAnalyzer out = ProcessTools.executeCommand(command);
            out.shouldHaveExitValue(0);
            out.shouldContain("FORWARDABLE");
            out.shouldContain("PROXIABLE");
        } catch(Throwable e) {
            System.out.println("Unexpected exception: " + e);
            e.printStackTrace(System.out);
            return false;
        }

        return true;
    }
}
