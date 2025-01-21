/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import jdk.internal.misc.CDS;

public class ArchivedIntegerHolder {
    public static Object[] archivedObjects;
    static {
        CDS.initializeFromArchive(ArchivedIntegerHolder.class);
        if (archivedObjects == null) {
            archivedObjects = new Object[256];
            for (int i = -128; i <= 127; i++) {
                archivedObjects[i + 128] = Integer.valueOf(i);
            }
        }
    }
}
