/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8284490
 * @summary Remove finalizer method in java.security.jgss
 * @key intermittent
 * @library /test/lib/
 * @build jdk.test.lib.util.ForceGC
 * @run main/othervm GssNameCleanup
 */

import java.lang.ref.WeakReference;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.GSSException;

import jdk.test.lib.util.ForceGC;

public final class GssNameCleanup {
    public static void main(String[] args) throws Exception {
        // Enable debug log so that the failure analysis could be easier.
        System.setProperty("sun.security.nativegss.debug", "true");

        // Use native provider
        System.setProperty("sun.security.jgss.native", "true");

        // Create an object
        GSSManager manager = GSSManager.getInstance();
        try {
            GSSName name =
                manager.createName("u1", GSSName.NT_USER_NAME);
            WeakReference<GSSName> weakRef = new WeakReference<>(name);
            name = null;

            // Check if the object has been collected.
            if (!ForceGC.wait(() -> weakRef.refersTo(null))) {
                throw new RuntimeException("GSSName object is not released");
            }
        } catch (GSSException gsse) {
            // createName() could fail if the local default realm
            // cannot be located.  Just ignore the test case for
            // such circumstances.
            System.out.println("Ignore this test case: " + gsse);
        }
    }
}

