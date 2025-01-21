/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import org.objectweb.asm.*;

class Asmator {
    static byte[] fixup(byte[] buf) throws java.io.IOException {
        ClassReader cr = new ClassReader(buf);
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM4, cw) {
            public MethodVisitor visitMethod(
                final int access,
                final String name,
                final String desc,
                final String signature,
                final String[] exceptions)
            {
                MethodVisitor mv = super.visitMethod(access,
                        name,
                        desc,
                        signature,
                        exceptions);
                if (mv == null)  return null;
                if (name.equals("callme")) {
                    // make receiver go dead!
                    mv.visitInsn(Opcodes.ACONST_NULL);
                    mv.visitVarInsn(Opcodes.ASTORE, 0);
                }
                return mv;
            }
        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}
