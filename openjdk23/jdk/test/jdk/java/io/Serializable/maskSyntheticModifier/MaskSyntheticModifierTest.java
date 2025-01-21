/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4897937
 * @run main MaskSyntheticModifierTest
 * @summary Verify that the presence of the JVM_ACC_SYNTHETIC bit in the
 *          modifiers of fields and methods does not affect default
 *          serialVersionUID calculation.
 */

import java.io.File;
import java.io.ObjectStreamClass;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MaskSyntheticModifierTest {
    public static void main(String[] args) throws Exception {
        setup();

        long suid = ObjectStreamClass.lookup(Foo.class).getSerialVersionUID();
        if (suid != 8027844768744011556L) {
            throw new Error("incorrect serialVersionUID: " + suid);
        }
    }

    private static void setup() throws Exception {
        // Copy the class file to the first component of the class path
        String cp = System.getProperty("java.class.path");
        String cp1 = cp.substring(0, cp.indexOf(File.pathSeparatorChar));
        Files.copy(Paths.get(System.getProperty("test.src"), "Foo.class"),
                Paths.get(cp1, "Foo.class"), StandardCopyOption.REPLACE_EXISTING);
    }
}
