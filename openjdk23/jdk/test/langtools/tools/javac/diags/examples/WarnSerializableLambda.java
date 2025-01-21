/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

// key: compiler.warn.access.to.member.from.serializable.element
// options: -XDwarnOnAccessToMembers

import java.io.Serializable;

public class WarnSerializableLambda {
    private void m1() {
        new SerializableClass() {
            @Override
            public void m() {
                packageField = "";
            }
        };
    }

    String packageField;

    class SerializableClass implements Serializable {
        public void m() {}
    }
}
