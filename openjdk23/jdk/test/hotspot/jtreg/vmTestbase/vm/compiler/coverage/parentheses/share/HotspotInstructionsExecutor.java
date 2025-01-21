/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package vm.compiler.coverage.parentheses.share;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;


import java.lang.reflect.Method;
import java.util.List;

/**
 * This class convert instructions sequence to java class file, load it to same JVM and then execute.
 * This class uses hidden classes for class loading
 */
public class HotspotInstructionsExecutor implements InstructionsExecutor {

    private static final Object[] NO_CP_PATCHES = new Object[0];

    private int stackSize;

    public HotspotInstructionsExecutor(int stackSize) {
        this.stackSize = stackSize;
    }

    @Override
    public int execute(List<Instruction> instructions) throws ReflectiveOperationException {
        Class execClass = generateClass(instructions);
        Method execMethod = execClass.getMethod("exec");
        return (Integer) execMethod.invoke(null);
    }

    private Class generateClass(List<Instruction> instructions) throws ReflectiveOperationException {
        // Needs to be in the same package as the lookup class.
        String classNameForASM = "vm/compiler/coverage/parentheses/share/ExecClass";

        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_1, ACC_PUBLIC, classNameForASM, null, "java/lang/Object", null);

        // creates a MethodWriter for the 'main' method
        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "exec", "()I", null, null);

        for (Instruction instruction : instructions) {
            mw.visitInsn(instruction.opCode);
        }

        mw.visitInsn(IRETURN);
        mw.visitMaxs(stackSize, 2);
        mw.visitEnd();

        Lookup lookup = MethodHandles.lookup();
        Class<?> hc = lookup.defineHiddenClass(cw.toByteArray(), false).lookupClass();
        return hc;
    }
}
