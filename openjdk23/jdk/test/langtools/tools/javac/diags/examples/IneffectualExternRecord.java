/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.warn.ineffectual.externalizable.method.record

// options: -Xlint:serial

import java.io.*;

record IneffectualExternRecord(int foo) implements Externalizable {
    @Override
    public void writeExternal(ObjectOutput oo) {
        ;
    }

    @Override
    public void readExternal(ObjectInput oi) {
        ;
    }
}
