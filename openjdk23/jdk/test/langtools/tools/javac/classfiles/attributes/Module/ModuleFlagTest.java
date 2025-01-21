/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8080878
 * @summary Checking ACC_MODULE flag is generated for module-info.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @build toolbox.ToolBox toolbox.JavacTask toolbox.ToolBox
 * @run main ModuleFlagTest
 */

import java.lang.classfile.AccessFlags;
import java.lang.classfile.ClassFile;
import java.lang.reflect.AccessFlag;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.JavacTask;
import toolbox.ToolBox;

public class ModuleFlagTest {
    public static void main(String[] args) throws IOException {
        Path outdir = Paths.get(".");
        ToolBox tb = new ToolBox();
        final Path moduleInfo = Paths.get("module-info.java");
        tb.writeFile(moduleInfo, "module test_module{}");
        new JavacTask(tb)
                .outdir(outdir)
                .files(moduleInfo)
                .run();

        AccessFlags accessFlags = ClassFile.of().parse(outdir.resolve("module-info.class"))
                .flags();
        if (!accessFlags.has(AccessFlag.MODULE)) {
            throw new RuntimeException("ClassFile doesn't have module access flag");
        }
    }
}
