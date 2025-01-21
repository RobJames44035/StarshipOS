/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5097015 8130181 8279222 8292739
 * @summary make sure we correctly treat Provider string entries as case insensitive
 * @author Andreas Sterbenz
 */

import java.security.*;
import java.security.Provider.*;

public class CaseSensitiveServices extends Provider {
    CaseSensitiveServices() {
        super("Foo", "1.0", null);
        put("MessageDigest.Foo", "com.Foo");
        put("mESSAGEdIGEST.fOO xYz", "aBc");
        // first assign the DEF alias to algorithm Foo
        put("ALg.aliaS.MESSAGEdigest.DEF", "FoO");
        put("messageDigest.Bar", "com.Bar");
        put("MESSAGEDIGEST.BAZ", "com.Baz");
        // reassign the DEF alias to algorithm Bar
        put("ALg.aliaS.MESSAGEdigest.DEF", "Bar");
        // invalid entry since it misses the corresponding impl class info
        // e.g. put("MessageDigest.Invalid", "implClass");
        put("MessageDigest.Invalid xYz", "aBc");
    }

    public static void main(String[] args) throws Exception {
        Provider p = new CaseSensitiveServices();

        System.out.println("Services: " + p.getServices());

        if (p.getServices().size() != 3) {
            throw new Exception("services.size() should be 3");
        }

        if (p.getService("MessageDigest", "Invalid") != null) {
            throw new Exception("Invalid service returned");
        }
        Service s = testService(p, "MessageDigest", "fOO");
        String val = s.getAttribute("Xyz");
        if ("aBc".equals(val) == false) {
            throw new Exception("Wrong value: " + val);
        }
        if (s.toString().indexOf("DEF") != -1) {
            throw new Exception("Old alias DEF should be removed");
        }

        // test Service alias DEF and its associated impl is Bar
        s = testService(p, "MessageDigest", "DeF");
        if (s.getAttribute("Xyz") != null) {
            throw new Exception("DEF mapped to the wrong impl");
        }
        testService(p, "MessageDigest", "BAR");
        testService(p, "MessageDigest", "baz");
        System.out.println("OK");
    }

    private static Service testService(Provider p, String type, String alg)
            throws Exception {
        System.out.println("Getting " + type + "." + alg + "...");
        Service s = p.getService(type, alg);
        System.out.println(s);
        if (s == null) {
            throw new Exception("Lookup failed for: " + type + "." + alg);
        }
        return s;
    }

}
