/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.security.logging;

import java.security.Security;
import java.util.List;

import jdk.test.lib.security.JDKSecurityProperties;

/*
 * @test
 * @bug 8148188
 * @summary Enhance the security libraries to record events of interest
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.security.logging.TestSecurityPropertyModificationLog LOGGING_ENABLED
 * @run main/othervm jdk.security.logging.TestSecurityPropertyModificationLog LOGGING_DISABLED
 */
public class TestSecurityPropertyModificationLog {

    static List<String> keys = JDKSecurityProperties.getKeys();
    static String keyValue = "shouldBecomeAnEvent";

    public static void main(String[] args) throws Exception {
        LogJvm l = new LogJvm(SetSecurityProperty.class, args);
        for (String s: keys) {
            l.addExpected("FINE: SecurityPropertyModification: key:" +
                    s + ", value:" + keyValue);
        }
        l.testExpected();
    }

    public static class SetSecurityProperty {
        public static void main(String[] args) {
            for (String s: keys) {
                Security.setProperty(s, keyValue);
            }
        }
    }
}
