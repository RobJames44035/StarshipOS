/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.io.File;
import java.io.FileOutputStream;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test TestMultiANewArray
 * @bug 8038076
 * @library /test/lib
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @compile -XDignore.symbol.file TestMultiANewArray.java
 * @run driver TestMultiANewArray 49
 * @run driver TestMultiANewArray 50
 * @run driver TestMultiANewArray 51
 * @run driver TestMultiANewArray 52
 */

public class TestMultiANewArray {
    public static void main(String... args) throws Exception {
        int cfv = Integer.parseInt(args[0]);
        writeClassFile(cfv);
        System.err.println("Running with cfv: " + cfv);
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("-cp", ".",  "ClassFile");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("VerifyError");
        output.shouldHaveExitValue(1);
    }

    public static void writeClassFile(int cfv) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(cfv, ACC_PUBLIC + ACC_SUPER, "ClassFile", null, "java/lang/Object", null);
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitInsn(ICONST_1);
        mv.visitInsn(ICONST_2);
        mv.visitMultiANewArrayInsn("[I", 2);
        mv.visitVarInsn(ASTORE, 1);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        cw.visitEnd();

        try (FileOutputStream fos = new FileOutputStream(new File("ClassFile.class"))) {
             fos.write(cw.toByteArray());
        }
    }
}
