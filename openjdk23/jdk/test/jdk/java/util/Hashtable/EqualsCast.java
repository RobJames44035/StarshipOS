/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4208530
 * @summary Hashtable was less robust to extension that it could have been
 *          because the equals and Hashcode methods used internals
 *          unnecessarily.  (java.security.Provider tickled this sensitivity.)
 */

import java.security.Provider;
import java.util.Map;

public class EqualsCast {
    public static void main(String[] args) throws Exception {
        Map m1 = new MyProvider("foo", 69, "baz");
        Map m2 = new MyProvider("foo", 69, "baz");
        m1.equals(m2);
    }
}

class MyProvider extends Provider {

    private String name;

    public MyProvider(String name, double version, String info) {
        super(name, version, info);
        this.name = name;
        put("Signature.sigalg", "sigimpl");
    }

    public String getName() {
        return this.name;
    }
}
