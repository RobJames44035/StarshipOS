/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.cp.share;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassWriterExt;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import vm.mlvm.share.ClassfileGenerator;

public class GenCPFullOfMT extends GenFullCP {

    public static void main(String[] args) {
        ClassfileGenerator.main(args);
    }

    @Override
    protected void generateCommonData(ClassWriterExt cw) {
        cw.setCacheMTypes(false);
        super.generateCommonData(cw);
    }

    @Override
    protected void generateCPEntryData(ClassWriter cw, MethodVisitor mw) {
        mw.visitLdcInsn(Type.getMethodType("(FIZ)V"));
        mw.visitInsn(Opcodes.POP);
    }

}
