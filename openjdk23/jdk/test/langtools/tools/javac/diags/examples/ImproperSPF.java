/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.warn.improper.SPF
// key: compiler.warn.OSF.array.SPF
// key: compiler.warn.SPF.null.init

// options: -Xlint:serial

import java.io.Serializable;

class ImproperSPF implements Serializable {
    // Proper declaration of serialPersistentFields is:
    // private static final ObjectStreamField[] serialPersistentFields = ...
    public /*instance*/ Object serialPersistentFields = null;

    private static final long serialVersionUID = 42;
}
