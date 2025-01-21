/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8246193
 * @summary Possible NPE in ENC-PA-REP search in AS-REQ
 * @library /test/lib
 * @compile -XDignore.symbol.file AlwaysEncPaReq.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Dtest.kdc.always.enc.pa.rep
 *                   -Djdk.net.hosts.file=TestHosts AlwaysEncPaReq
 */

public class AlwaysEncPaReq {
    public static void main(String[] args) throws Exception {
        new OneKDC(null).writeJAASConf()
                .setOption(KDC.Option.PREAUTH_REQUIRED, false);
        Context.fromJAAS("client");
    }
}
