/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.vm.ci.code.site;

import static jdk.vm.ci.meta.MetaUtil.identityHashCodeString;

/**
 * Represents a code position with associated additional information.
 */
public abstract class Site {

    /**
     * The position (or offset) of this site with respect to the start of the target method.
     */
    public final int pcOffset;

    public Site(int pos) {
        this.pcOffset = pos;
    }

    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException("hashCode");
    }

    @Override
    public String toString() {
        return identityHashCodeString(this);
    }

    @Override
    public abstract boolean equals(Object obj);
}
