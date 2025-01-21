/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.data;

import vm.runtime.defmeth.shared.data.method.Method;

public abstract class ClazzImpl implements Clazz {

    /** Fixed major version of class file if needed.
     * "0" means default value (depends on the context). */
    int ver = 0; // class file major version

    String name;

    /** Access flags of the class.
     * "-1" means default (depends on the context). */
    int flags = -1;

    Method[] methods;

    /** Language-level signature of the class.
     * Represents information about generic parameters */
    String sig;

    ClazzImpl(String name, int ver, int flags, String sig, Method[] methods) {
        this.name = name;
        this.ver = ver;
        this.flags = flags;
        this.sig = sig;
        this.methods = methods;
    }

    public String name() {
        return name;
    }

    public int ver() {
        return ver;
    }

    public Method[] methods() {
        return methods;
    }

    public int flags() {
        return flags;
    }

    public String sig() {
        return sig;
    }

    /** Class name in VM-internal format */
    public String intlName() {
        return name.replaceAll("\\.","/");
    }

    public String getShortName() {
        return name.replaceAll(".*\\.","");
    }
}
