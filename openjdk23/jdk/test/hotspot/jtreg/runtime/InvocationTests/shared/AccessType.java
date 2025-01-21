/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package shared;

import static org.objectweb.asm.Opcodes.*;

public enum AccessType {
      PUBLIC           ("PUB")   { public int value() { return ACC_PUBLIC; } }
    , PROTECTED        ("PROT")  { public int value() { return ACC_PROTECTED; } }
    , PACKAGE_PRIVATE  ("PP")    { public int value() { return 0; } }
    , PRIVATE          ("PRIV")  { public int value() { return ACC_PRIVATE; } }
    , UNDEF            ("UNDEF") { public int value() { return -1; } }
    ;

    private String name;

    AccessType(String name) {
        this.name = name;
    }

    public abstract int value();

    public String toString() {
        return name;
    }
};
