/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package jdk.vm.ci.hotspot;

import java.util.Objects;

import jdk.vm.ci.meta.Constant;
import jdk.vm.ci.meta.VMConstant;

final class HotSpotMetaspaceConstantImpl implements HotSpotMetaspaceConstant, VMConstant {

    static HotSpotMetaspaceConstantImpl forMetaspaceObject(MetaspaceObject metaspaceObject, boolean compressed) {
        return new HotSpotMetaspaceConstantImpl(metaspaceObject, compressed);
    }

    static MetaspaceObject getMetaspaceObject(Constant constant) {
        return ((HotSpotMetaspaceConstantImpl) constant).metaspaceObject;
    }

    private final MetaspaceObject metaspaceObject;
    private final boolean compressed;

    private HotSpotMetaspaceConstantImpl(MetaspaceObject metaspaceObject, boolean compressed) {
        this.metaspaceObject = metaspaceObject;
        this.compressed = compressed;
        if (compressed && !canBeStoredInCompressibleMetaSpace()) {
            throw new IllegalArgumentException("constant cannot be compressed: " + metaspaceObject);
        }
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(metaspaceObject) ^ (compressed ? 1 : 2);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HotSpotMetaspaceConstantImpl)) {
            return false;
        }

        HotSpotMetaspaceConstantImpl other = (HotSpotMetaspaceConstantImpl) o;
        return Objects.equals(this.metaspaceObject, other.metaspaceObject) && this.compressed == other.compressed;
    }

    @Override
    public String toValueString() {
        return String.format("meta{%s%s}", metaspaceObject, compressed ? ";compressed" : "");
    }

    @Override
    public String toString() {
        return toValueString();
    }

    @Override
    public boolean isDefaultForKind() {
        return false;
    }

    @Override
    public boolean isCompressed() {
        return compressed;
    }

    @Override
    public boolean isCompressible() {
        if (compressed) {
            return false;
        }
        return canBeStoredInCompressibleMetaSpace();
    }

    private boolean canBeStoredInCompressibleMetaSpace() {
        if (metaspaceObject instanceof HotSpotResolvedJavaType t && !t.isArray()) {
            // As of JDK-8338526, interface and abstract types are not stored
            // in compressible metaspace.
            return !t.isInterface() && !t.isAbstract();
        }
        return true;
    }

    @Override
    public Constant compress() {
        if (compressed) {
            throw new IllegalArgumentException("already compressed: " + this);
        }
        HotSpotMetaspaceConstantImpl res = HotSpotMetaspaceConstantImpl.forMetaspaceObject(metaspaceObject, true);
        assert res.isCompressed();
        return res;
    }

    @Override
    public Constant uncompress() {
        if (!compressed) {
            throw new IllegalArgumentException("not compressed: " + this);
        }
        HotSpotMetaspaceConstantImpl res = HotSpotMetaspaceConstantImpl.forMetaspaceObject(metaspaceObject, false);
        assert !res.isCompressed();
        return res;
    }

    @Override
    public HotSpotResolvedObjectType asResolvedJavaType() {
        if (metaspaceObject instanceof HotSpotResolvedObjectType) {
            return (HotSpotResolvedObjectType) metaspaceObject;
        }
        return null;
    }

    @Override
    public HotSpotResolvedJavaMethod asResolvedJavaMethod() {
        if (metaspaceObject instanceof HotSpotResolvedJavaMethod) {
            return (HotSpotResolvedJavaMethod) metaspaceObject;
        }
        return null;
    }
}
