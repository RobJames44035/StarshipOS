/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package invokevirtual;

import org.objectweb.asm.Opcodes;
import shared.GenericClassGenerator;

/*******************************************************************/
class ClassGenerator extends GenericClassGenerator<ClassGenerator> {
    public ClassGenerator(String fullClassName) {
        super(fullClassName);
    }

    public ClassGenerator(String fullClassName, String parentClassName) {
        super(fullClassName, parentClassName);
    }

    public ClassGenerator(String fullClassName, String parentClassName, int flags) {
        super(fullClassName, parentClassName, flags);
    }

    // Add target method call site into current class
    public ClassGenerator addCaller(String targetClass) {
        return super.addCaller(targetClass, Opcodes.INVOKEVIRTUAL);
    }
}
