/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @bug 4634392
 * @summary JDK code doesn't respect contract for equals and hashCode
 * @author Andrew Fan
 */

import org.ietf.jgss.*;

public class Krb5NameEquals {

    private static String NAME_STR1 = "service@localhost";
    private static String NAME_STR2 = "service2@localhost";
    private static final Oid MECH;

    static {
        Oid temp = null;
        try {
            temp = new Oid("1.2.840.113554.1.2.2"); // KRB5
        } catch (Exception e) {
            // should never happen
        }
        MECH = temp;
    }

    public static void main(String[] argv) throws Exception {
        GSSManager mgr = GSSManager.getInstance();

        boolean result = true;
        // Create GSSName and check their equals(), hashCode() impl
        GSSName name1 = mgr.createName(NAME_STR1,
            GSSName.NT_HOSTBASED_SERVICE, MECH);
        GSSName name2 = mgr.createName(NAME_STR2,
            GSSName.NT_HOSTBASED_SERVICE, MECH);
        GSSName name3 = mgr.createName(NAME_STR1,
            GSSName.NT_HOSTBASED_SERVICE, MECH);

        if (!name1.equals(name1) || !name1.equals(name3) ||
            !name1.equals((Object) name1) ||
            !name1.equals((Object) name3)) {
            System.out.println("Error: should be the same name");
            result = false;
        } else if (name1.hashCode() != name3.hashCode()) {
            System.out.println("Error: should have same hash");
            result = false;
        }

        if (name1.equals(name2) || name1.equals((Object) name2)) {
            System.out.println("Error: should be different names");
            result = false;
        }
        if (result) {
            System.out.println("Done");
        } else System.exit(1);
    }
}
