/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package invokeinterface;

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

    public ClassGenerator(String fullClassName, String parentClassName, int flags, String[] implementedInterfaces) {
        super(fullClassName, parentClassName, flags, implementedInterfaces);
    }

    // Add target method call site into current class
    public ClassGenerator addCaller(String targetClass) {
        return super.addCaller(targetClass, Opcodes.INVOKEINTERFACE);
    }
}
