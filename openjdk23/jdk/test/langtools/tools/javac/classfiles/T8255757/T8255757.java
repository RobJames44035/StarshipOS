/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8255757
 * @summary Javac shouldn't emit duplicate pool entries on array::clone
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main T8255757
 */

import java.nio.file.Path;

import java.lang.classfile.*;
import java.lang.classfile.constantpool.*;

import toolbox.JavacTask;
import toolbox.ToolBox;
import toolbox.TestRunner;

public class T8255757 extends TestRunner {
    ToolBox tb;

    T8255757() {
        super(System.err);
        tb = new ToolBox();
    }

    public static void main(String[] args) throws Exception {
        T8255757 t = new T8255757();
        t.runTests();
    }

    @Test
    public void testDuplicatePoolEntries() throws Exception {
        String code = """
                public class Test {
                    void test(Object[] o) {
                        o.clone();
                        o.clone();
                    }
                    void test2(Object[] o) {
                        o.clone();
                        o.clone();
                    }
                }""";
        Path curPath = Path.of(".");
        new JavacTask(tb)
                .sources(code)
                .outdir(curPath)
                .run();

        ClassModel cf = ClassFile.of().parse(curPath.resolve("Test.class"));
        int num = 0;
        for (PoolEntry pe : cf.constantPool()) {
            if (pe instanceof MethodRefEntry methodRefEntry) {
                String class_name = methodRefEntry.owner().asInternalName();
                String method_name = methodRefEntry.name().stringValue();
                String method_type = methodRefEntry.type().stringValue();
                if ("[Ljava/lang/Object;".equals(class_name) &&
                        "clone".equals(method_name) &&
                        "()Ljava/lang/Object;".equals(method_type)) {
                    ++num;
                }
            }
        }
        if (num != 1) {
            throw new AssertionError("The number of the pool entries on array::clone is not right. " +
                    "Expected number: 1, actual number: " + num);
        }
    }
}
