/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8001326
 * @run main/othervm ReplayCacheExpunge 16
 * @run main/othervm/fail ReplayCacheExpunge 15
 * @summary when number of expired entries minus number of good entries
 * is more than 30, expunge occurs, and expired entries are forgotten.
*/

import java.util.Random;
import sun.security.krb5.internal.KerberosTime;
import sun.security.krb5.internal.ReplayCache;
import sun.security.krb5.internal.rcache.AuthTimeWithHash;

public class ReplayCacheExpunge {
    static final String client = "dummy@REALM";
    static final String server = "server/localhost@REALM";
    static final Random rand = new Random();

    public static void main(String[] args) throws Exception {
        // Make sure clockskew is default value
        System.setProperty("java.security.krb5.conf", "nothing");

        int count = Integer.parseInt(args[0]);
        ReplayCache cache = ReplayCache.getInstance("dfl:./");
        AuthTimeWithHash a1 =
                new AuthTimeWithHash(client, server, time(-400), 0, "HASH", hash("1"));
        AuthTimeWithHash a2 =
                new AuthTimeWithHash(client, server, time(0), 0, "HASH", hash("4"));
        KerberosTime now = new KerberosTime(time(0)*1000L);
        KerberosTime then = new KerberosTime(time(-300)*1000L);

        // Once upon a time, we added a lot of events
        for (int i=0; i<count; i++) {
            a1 = new AuthTimeWithHash(client, server, time(-400), 0, "HASH", hash(""));
            cache.checkAndStore(then, a1);
        }

        // Now, we add a new one. If some conditions hold, the old ones
        // will be expunged.
        cache.checkAndStore(now, a2);

        // and adding an old one will not trigger any error
        cache.checkAndStore(now, a1);
    }

    private static String hash(String s) {
        char[] data = new char[16];
        for (int i=0; i<16; i++) {
            if (i < s.length()) data[i] = s.charAt(i);
            else data[i] = (char)('0' + rand.nextInt(10));
        }
        return new String(data);
    }

    private static int time(int x) {
        return (int)(System.currentTimeMillis()/1000) + x;
    }
}
