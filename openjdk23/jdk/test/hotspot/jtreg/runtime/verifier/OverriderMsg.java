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
 * @test OverriderMsg
 * @bug 8026894
 * @library /test/lib
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @compile -XDignore.symbol.file OverriderMsg.java
 * @run driver OverriderMsg
 */

// This test checks that the super class name is included in the message when
// a method is detected overriding a final method in its super class.  The
// asm part of the test creates these two classes:
//
//     public class HasFinal {
//         public final void m(String s) { }
//     }
//
//     public class Overrider extends HasFinal {
//         public void m(String s) { }
//         public static void main(String[] args) { }
//     }
//
public class OverriderMsg {

    public static void dump_HasFinal () throws Exception {

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "HasFinal", null, "java/lang/Object", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_FINAL, "m", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 2);
            mv.visitEnd();
        }
        cw.visitEnd();
        try (FileOutputStream fos = new FileOutputStream(new File("HasFinal.class"))) {
             fos.write(cw.toByteArray());
        }
    }


    public static void dump_Overrider () throws Exception {

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "Overrider", null, "HasFinal", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "HasFinal", "<init>", "()V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "m", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        try (FileOutputStream fos = new FileOutputStream(new File("Overrider.class"))) {
             fos.write(cw.toByteArray());
        }
    }


    public static void main(String... args) throws Exception {
        dump_HasFinal();
        dump_Overrider();
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("-cp", ".",  "Overrider");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain(
            "java.lang.IncompatibleClassChangeError: class Overrider overrides final method HasFinal.m(Ljava/lang/String;)V");
        output.shouldHaveExitValue(1);
    }

}
