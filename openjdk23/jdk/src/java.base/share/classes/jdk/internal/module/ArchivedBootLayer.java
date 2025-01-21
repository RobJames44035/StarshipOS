/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package jdk.internal.module;

import jdk.internal.misc.CDS;

/**
 * Used by ModuleBootstrap for archiving the boot layer.
 */
class ArchivedBootLayer {
    private static ArchivedBootLayer archivedBootLayer;

    private final ModuleLayer bootLayer;

    private ArchivedBootLayer(ModuleLayer bootLayer) {
        this.bootLayer = bootLayer;
    }

    ModuleLayer bootLayer() {
        return bootLayer;
    }

    static ArchivedBootLayer get() {
        return archivedBootLayer;
    }

    static void archive(ModuleLayer layer) {
        archivedBootLayer = new ArchivedBootLayer(layer);
    }

    static {
        CDS.initializeFromArchive(ArchivedBootLayer.class);
    }
}
