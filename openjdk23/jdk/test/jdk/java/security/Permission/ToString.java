/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6549506
 * @summary Specification of Permission.toString() method contradicts with
 *      JDK implementation
 */

import java.security.*;

public class ToString {

    public static void main(String[]args) throws Exception {
        DummyWritePermission dummyPerm = new DummyWritePermission();
        NullActionPermission nullActionPerm = new NullActionPermission();
        System.out.println(dummyPerm.toString());
        System.out.println(dummyPerm.getDescription());
        System.out.println(nullActionPerm.toString());
        System.out.println(nullActionPerm.getDescription());
        if (!dummyPerm.toString().equals(dummyPerm.getDescription())) {
            throw new Exception("The expected permission.toString() is " +
                dummyPerm.getDescription() + ", but " +
                dummyPerm.toString() + " returned!");
        }

        if (!nullActionPerm.toString().equals(nullActionPerm.getDescription())) {
            throw new Exception("The expected permission.toString() is " +
                nullActionPerm.getDescription() + ", but " +
                nullActionPerm.toString() + " returned!");
        }
    }

    private static abstract class SimplePermission extends Permission {
        public SimplePermission(String name) {
            super(name);
        }

        public boolean implies(Permission permission) {
            return false;
        }

        public boolean equals(Object obj) {
            return false;
        }

        public int hashCode() {
            return 13;
        }
    }

    private static class DummyWritePermission extends SimplePermission {
        public DummyWritePermission() {
            super("permit to");
        }

        public String getActions() {
            return "write";
        }

        public String getDescription() {
            return "(\"ToString$DummyWritePermission\" \"permit to\" \"write\")";
        }
    }

    private static class NullActionPermission extends SimplePermission {
        public NullActionPermission() {
            super("permit to");
        }

        public String getActions() {
            return null;
        }

        public String getDescription() {
            return "(\"ToString$NullActionPermission\" \"permit to\")";
        }
    }

}
