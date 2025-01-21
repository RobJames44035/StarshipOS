/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.lang.module.ModuleFinder;
import static jdk.test.lib.Asserts.*;

/*
 * @test
 * @bug 8308398
 * @library /test/lib
 * @summary Verify jdk.crypto.ec empty module
 * @run main ecModuleCheck
 */

/* This test verifies jdk.crypto.ec is in the image, but not resolvable.
 */
public class ecModuleCheck {
    public static void main(String[] args) throws Exception {
        // True if module is found in the image.
        assertTrue(ModuleFinder.ofSystem().find("jdk.crypto.ec").isPresent(),
            "jdk.crypto.ec was not found in image.");
        // Since the module empty, isPresent() should be false.
        assertFalse(ModuleLayer.boot().findModule("jdk.crypto.ec").
            isPresent(), "jdk.crypto.ec shouldn't be resolvable.");
    }
}
