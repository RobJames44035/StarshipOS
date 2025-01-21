/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.warn.ineffectual.extern.method.enum

// options: -Xlint:serial

import java.io.*;

enum IneffectualExternEnum implements Externalizable {
    INSTANCE;

    @Override
    public void writeExternal(ObjectOutput oo) {
        ;
    }

    @Override
    public void readExternal(ObjectInput oi) {
        ;
    }
}
