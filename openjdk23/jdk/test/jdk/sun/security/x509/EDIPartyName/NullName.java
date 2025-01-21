/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/* @test
 * @bug 8296741
 * @summary Illegal X400Address and EDIPartyName should not be created
 * @library /test/lib
 * @modules java.base/sun.security.x509
 */

import jdk.test.lib.Utils;
import sun.security.x509.EDIPartyName;

public class NullName {

    public static void main(String[] argv) throws Exception {
        Utils.runAndCheckException(
                () -> new EDIPartyName((String)null),
                NullPointerException.class);
        Utils.runAndCheckException(
                () -> new EDIPartyName(null, null),
                NullPointerException.class);
        Utils.runAndCheckException(
                () -> new EDIPartyName("hello", null),
                NullPointerException.class);
        new EDIPartyName(null, "hello");
    }
}
