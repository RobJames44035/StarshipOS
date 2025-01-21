/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8044500
 * @summary Add kinit options and krb5.conf flags that allow users to
 *          obtain renewable tickets and specify ticket lifetimes
 * @modules java.security.jgss/sun.security.krb5
 * @compile -XDignore.symbol.file Duration.java
 * @run main Duration
 */
import sun.security.krb5.Config;
import sun.security.krb5.KrbException;

public class Duration {
    public static void main(String[] args) throws Exception {
        check("123", 123);
        check("1:1", 3660);
        check("1:1:1", 3661);
        check("1d", 86400);
        check("1h", 3600);
        check("1h1m", 3660);
        check("1h 1m", 3660);
        check("1d 1h 1m 1s", 90061);
        check("1d1h1m1s", 90061);

        check("", -1);
        check("abc", -1);
        check("1ms", -1);
        check("1d1d", -1);
        check("1h1d", -1);
        check("x1h", -1);
        check("1h x 1m", -1);
        check(":", -1);
        check("1:60", -1);
        check("1:1:1:1", -1);
        check("1:1:1:", -1);
    }

    static void check(String s, int ex) throws Exception {
        System.out.print("\u001b[1;37;41m" +s + " " + ex);
        System.out.print("\u001b[m\n");
        try {
            int result = Config.duration(s);
            if (result != ex) throw new Exception("for " + s + " is " + result);
        } catch (KrbException ke) {
            ke.printStackTrace();
            if (ex != -1) throw new Exception();
        }
    }
}
