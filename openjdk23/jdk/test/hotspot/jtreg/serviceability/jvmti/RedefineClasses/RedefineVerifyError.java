/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 6402717 8330606
 * @summary Redefine VerifyError to get a VerifyError should not throw SOE
 * @requires vm.jvmti
 * @library /test/lib
 * @library /testlibrary/asm
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.instrument
 *          jdk.jartool/sun.tools.jar
 * @run main RedefineClassHelper
 * @run main/othervm/timeout=180
 *         -javaagent:redefineagent.jar
 *         -Xlog:class+init,exceptions
 *         RedefineVerifyError
 */

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.RecordComponentVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

public class RedefineVerifyError implements Opcodes {

    // This is a redefinition of java.lang.VerifyError with two broken init methods (no bytecodes)
    public static byte[] dump () throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(52, ACC_SUPER | ACC_PUBLIC, "java/lang/VerifyError", null, "java/lang/LinkageError", null);
        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_FINAL | ACC_STATIC, "serialVersionUID", "J", null, new Long(7001962396098498785L));
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitEnd();
        }

        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/String;)V", null, null);
            methodVisitor.visitCode();
            classWriter.visitEnd();
        }

        return classWriter.toByteArray();
    }

    public static void main(String[] args) throws Exception {

        Class<?> verifyErrorMirror = java.lang.VerifyError.class;

        try {
            // The Verifier is called for the redefinition, which will fail because of the broken <init> method above.
            RedefineClassHelper.redefineClass(verifyErrorMirror, dump());
            throw new RuntimeException("This should throw VerifyError");
        } catch (VerifyError e) {
            // JVMTI recreates the VerifyError so the verification message is lost.
            System.out.println("Passed");
        }
    }
}
