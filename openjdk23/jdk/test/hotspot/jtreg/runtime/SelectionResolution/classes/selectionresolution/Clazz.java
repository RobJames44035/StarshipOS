/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package selectionresolution;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.V1_8;


class Clazz extends ClassConstruct {

    /**
     * Construct a Class
     * @param name Name of Class
     * @param access Access for the Class
     */
    public Clazz(String name, int access, int index) {
        this(name, null, access, V1_8, index, new String[] { });
    }

    /**
     * Construct a Class
     * @param name Name of Class
     * @param extending Class being extended
     * @param access Access for the Class
     */
    public Clazz(String name, String extending, int access, int index) {
        this(name, extending, access, V1_8, index, new String[] { });
    }

    /**
     * Construct a Class
     * @param name Name of Class
     * @param extending Class being extended
     * @param access access for the Class
     * @param implementing Interfaces implemented
     */
    public Clazz(String name, String extending, int access, int index, String... implementing) {
        this(name, extending, access, V1_8, index, implementing);
    }

    /**
     * Construct a Class
     * @param name Name of Class
     * @param extending Class being extended
     * @param access Access for the Class
     * @param classFileVersion Class file version
     * @param implementing Interfaces implemented
     */
    public Clazz(String name, String extending, int access, int classFileVersion, int index, String... implementing) {
        super(name, extending == null ? "java/lang/Object" : extending, access | ACC_SUPER, classFileVersion, index, implementing);
        // Add the default constructor
        addMethod("<init>", "()V", ACC_PUBLIC).makeConstructor(extending, false);
    }
}
