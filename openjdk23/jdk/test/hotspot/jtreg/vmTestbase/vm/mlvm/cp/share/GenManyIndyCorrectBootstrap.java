/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package vm.mlvm.cp.share;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassWriterExt;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import vm.mlvm.share.ClassfileGenerator;
import vm.mlvm.share.Env;

public class GenManyIndyCorrectBootstrap extends GenFullCP {

    /**
     * Generates a class file and writes it to a file
     * @see vm.mlvm.share.ClassfileGenerator
     * @param args Parameters for ClassfileGenerator.main() method
     */
    public static void main(String[] args) {
        ClassfileGenerator.main(args);
    }

    /**
     * Creates static init method, which constructs a call site object, which refers to the target method
     * and invokes Dummy.setMH() on this call site
     * @param cw Class writer object
     */
    @Override
    protected void createClassInitMethod(ClassWriter cw) {
        MethodVisitor mw = cw.visitMethod(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
                STATIC_INIT_METHOD_NAME, INIT_METHOD_SIGNATURE,
                null,
                new String[0]);

        mw.visitMethodInsn(Opcodes.INVOKESTATIC, JLI_METHODHANDLES, "lookup", "()" + fd(JLI_METHODHANDLES_LOOKUP));
        mw.visitLdcInsn(Type.getObjectType(fullClassName));
        mw.visitLdcInsn(TARGET_METHOD_NAME);
        mw.visitLdcInsn(TARGET_METHOD_SIGNATURE);
        mw.visitLdcInsn(Type.getObjectType(fullClassName));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JL_CLASS,
                "getClassLoader", "()" + fd(JL_CLASSLOADER));
        mw.visitMethodInsn(Opcodes.INVOKESTATIC, JLI_METHODTYPE,
                "fromMethodDescriptorString", "(" + fd(JL_STRING) + fd(JL_CLASSLOADER) + ")" + fd(JLI_METHODTYPE));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JLI_METHODHANDLES_LOOKUP,
                "findStatic", "(" + fd(JL_CLASS) + fd(JL_STRING) + fd(JLI_METHODTYPE) + ")" + fd(JLI_METHODHANDLE));
        mw.visitMethodInsn(Opcodes.INVOKESTATIC, NEW_INVOKE_SPECIAL_CLASS_NAME,
                "setMH", "(" + fd(JLI_METHODHANDLE) + ")V");

        finishMethodCode(mw);
    }

    /**
     * Disables invoke dynamic CP entry caching and generate default common data
     * @param cw Class writer object
     */
    @Override
    protected void generateCommonData(ClassWriterExt cw) {
        cw.setCacheInvokeDynamic(false);
        super.generateCommonData(cw);
    }

    /**
     * Generates an invokedynamic instruction (plus CP entry)
     * which has a valid reference kind in the CP method handle entry for the bootstrap method
     * @param cw Class writer object
     * @param mw Method writer object
     */
    @Override
    protected void generateCPEntryData(ClassWriter cw, MethodVisitor mw) {
        Handle bsm;
        if (Env.getRNG().nextBoolean()) {
            bsm = new Handle(Opcodes.H_NEWINVOKESPECIAL,
                    NEW_INVOKE_SPECIAL_CLASS_NAME,
                    INIT_METHOD_NAME,
                    NEW_INVOKE_SPECIAL_BOOTSTRAP_METHOD_SIGNATURE);
        } else {
            bsm = new Handle(Opcodes.H_INVOKESTATIC,
                    this.fullClassName,
                    BOOTSTRAP_METHOD_NAME,
                    BOOTSTRAP_METHOD_SIGNATURE);
        }
        mw.visitInvokeDynamicInsn(TARGET_METHOD_NAME,
                TARGET_METHOD_SIGNATURE,
                bsm);
    }
}
