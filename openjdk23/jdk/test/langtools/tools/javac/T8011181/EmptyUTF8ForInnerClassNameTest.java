/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8011181
 * @summary javac, empty UTF8 entry generated for inner class
 * @modules jdk.compiler/com.sun.tools.javac.util
 */

import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.tools.javac.util.Assert;
import java.lang.classfile.*;
import java.lang.classfile.constantpool.*;

public class EmptyUTF8ForInnerClassNameTest {

    public static void main(String[] args) throws Exception {
        new EmptyUTF8ForInnerClassNameTest().run();
    }

    void run() throws Exception {
        checkClassFile(Paths.get(System.getProperty("test.classes"),
                this.getClass().getName() + "$1.class"));
        checkClassFile(Paths.get(System.getProperty("test.classes"),
                this.getClass().getName() + "$EnumPlusSwitch.class"));
    }

    void checkClassFile(final Path path) throws Exception {
        ClassModel classFile = ClassFile.of().parse(
                new BufferedInputStream(Files.newInputStream(path)).readAllBytes());
        for (PoolEntry pe : classFile.constantPool()) {
            if (pe instanceof Utf8Entry utf8Info) {
                Assert.check(utf8Info.stringValue().length() > 0,
                        "UTF8 with length 0 found at class " + classFile.thisClass().name());
            }
        }
    }

    static class EnumPlusSwitch {

        public int m (Thread.State e) {
            switch (e) {
                case NEW:
                    return 0;
            }
            return -1;
        }
    }

}
