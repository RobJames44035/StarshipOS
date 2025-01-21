/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6568872
 * @summary BasicPermission.newPermissionCollection() violates general contract specified in Permission class
 */
public class MixedBasicPermissions {
    public static void main(String[] args) {
        try {
            new java.net.NetPermission("1.1.1.1", "connect").newPermissionCollection().add(new java.util.PropertyPermission("j", "read"));
        } catch (Exception e) {
            return;
            // Correct place
        }
        throw new RuntimeException("Should not be here");
    }
}
