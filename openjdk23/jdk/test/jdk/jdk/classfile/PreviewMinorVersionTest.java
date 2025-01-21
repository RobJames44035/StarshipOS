/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
import java.lang.classfile.ClassFile;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static java.lang.constant.ConstantDescs.*;
import static java.lang.classfile.ClassFile.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 * @test
 * @bug 8311172
 * @run junit PreviewMinorVersionTest
 * @summary Ensures ClassFile.PREVIEW_MINOR_VERSION equals that of classes with
 *          preview minor version from ClassModel::minorVersion
 */
public class PreviewMinorVersionTest {

    @Test
    public void testMinorVersionMatches() {
        // compile a class with --enable-preview
        // uses Record feature to trigger forcePreview
        var cf = ClassFile.of();
        var cd = ClassDesc.of("Test");
        var bytes = cf.build(cd, cb -> cb
                .withSuperclass(CD_Object)
                // old preview minor version,
                // with all bits set to 1
                .withVersion(JAVA_17_VERSION, -1)
        );

        var cm = ClassFile.of().parse(bytes);
        assertEquals(ClassFile.PREVIEW_MINOR_VERSION, cm.minorVersion());
    }
}
