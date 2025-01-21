/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 6879540 8194486
 * @summary enable empty password for kerberos 5
 * @library /test/lib
 * @compile -XDignore.symbol.file EmptyPassword.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts EmptyPassword
 */

public class EmptyPassword {

    public static void main(String[] args)
            throws Exception {

        OneKDC kdc = new OneKDC("aes128-cts");
        kdc.addPrincipal("empty", "".toCharArray());

        Context c = Context.fromUserPass("empty", "".toCharArray(), false);
        c.status();
    }
}

