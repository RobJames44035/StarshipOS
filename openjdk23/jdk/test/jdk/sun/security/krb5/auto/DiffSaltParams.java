/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8186831 8194486
 * @summary Kerberos ignores PA-DATA with a non-null s2kparams
 * @library /test/lib
 * @compile -XDignore.symbol.file DiffSaltParams.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Dsun.security.krb5.debug=true
 *      -Djdk.net.hosts.file=TestHosts DiffSaltParams
 */

public class DiffSaltParams {

    public static void main(String[] args) throws Exception {

        OneKDC kdc = new OneKDC(null).writeJAASConf();
        kdc.addPrincipal("user1", "user1pass".toCharArray(),
                "hello", new byte[]{0, 0, 1, 0});
        kdc.addPrincipal("user2", "user2pass".toCharArray(),
                "hello", null);
        kdc.addPrincipal("user3", "user3pass".toCharArray(),
                null, new byte[]{0, 0, 1, 0});
        kdc.addPrincipal("user4", "user4pass".toCharArray());

        Context.fromUserPass("user1", "user1pass".toCharArray(), true);
        Context.fromUserPass("user2", "user2pass".toCharArray(), true);
        Context.fromUserPass("user3", "user3pass".toCharArray(), true);
        Context.fromUserPass("user4", "user4pass".toCharArray(), true);
    }
}
