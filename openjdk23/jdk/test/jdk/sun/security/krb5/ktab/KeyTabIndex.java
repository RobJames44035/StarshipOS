/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */
/*
 * @test
 * @bug 6919610
 * @summary KeyTabInputStream uses static field for per-instance value
 * @modules java.security.jgss/sun.security.krb5
 *          java.security.jgss/sun.security.krb5.internal.ktab
 */
import sun.security.krb5.PrincipalName;
import sun.security.krb5.internal.ktab.KeyTab;

public class KeyTabIndex {
    public static void main(String[] args) throws Exception {
        KeyTab kt = KeyTab.create("ktab");
        // Two entries with very different length, so that it's easy to
        // observice the abnormal change of "index" field.
        kt.addEntry(new PrincipalName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@A"),
                "x".toCharArray(), 1, true);
        kt.addEntry(new PrincipalName("a@A"), "x".toCharArray(), 1, true);
        kt.save();
        Runnable t = new Runnable() {
            @Override
            public void run() {
                KeyTab.getInstance("ktab").getClass();
            }
        };
        for (int i=0; i<10; i++) {
            new Thread(t).start();
        }
    }
}
