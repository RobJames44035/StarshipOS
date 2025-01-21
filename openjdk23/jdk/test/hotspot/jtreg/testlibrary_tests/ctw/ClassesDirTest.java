/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8012447
 * @library /test/lib /testlibrary/ctw/src
 * @modules java.base/jdk.internal.access
 *          java.base/jdk.internal.jimage
 *          java.base/jdk.internal.misc
 *          java.base/jdk.internal.reflect
 *          java.management
 * @build jdk.test.whitebox.WhiteBox Foo Bar
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox Foo Bar
 * @run driver ClassesDirTest prepare
 * @run driver ClassesDirTest compile classes
 * @run driver ClassesDirTest check
 * @summary testing of CompileTheWorld :: classes in directory
 * @author igor.ignatyev@oracle.com
 */

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ClassesDirTest extends CtwTest {
    private static final String[] SHOULD_CONTAIN
            = {"# dir: classes", "Done (2 classes, 6 methods, "};

    private ClassesDirTest() {
        super(SHOULD_CONTAIN);
    }

    public static void main(String[] args) throws Exception {
        new ClassesDirTest().run(args);
    }

    protected void prepare() throws Exception {
        String path = "classes";
        Files.createDirectory(Paths.get(path));
        Files.move(Paths.get("Foo.class"), Paths.get(path, "Foo.class"),
                StandardCopyOption.REPLACE_EXISTING);
        Files.move(Paths.get("Bar.class"), Paths.get(path, "Bar.class"),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
