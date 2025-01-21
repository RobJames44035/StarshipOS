/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package jdk.vm.ci.meta;

/**
 * Describes the type and bytecode index range in which a local variable is live.
 */
public class Local {

    private final String name;
    private final int startBci;
    private final int endBci;
    private final int slot;
    private final JavaType type;

    public Local(String name, JavaType type, int startBci, int endBci, int slot) {
        this.name = name;
        this.startBci = startBci;
        this.endBci = endBci;
        this.slot = slot;
        this.type = type;
    }

    /**
     * Returns the first BCI at which this local has a value (inclusive).
     */
    public int getStartBCI() {
        return startBci;
    }


    /**
     * Returns the last BCI at which this local has a value (inclusive).
     * If the value returned is less than {@link #getStartBCI}, this object denotes a local
     * variable that is never live.
     */
    public int getEndBCI() {
        return endBci;
    }

    public String getName() {
        return name;
    }

    public JavaType getType() {
        return type;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Local)) {
            return false;
        }
        Local that = (Local) obj;
        return this.name.equals(that.name) && this.startBci == that.startBci && this.endBci == that.endBci && this.slot == that.slot && this.type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "LocalImpl<name=" + name + ", type=" + type + ", startBci=" + startBci + ", endBci=" + endBci + ", slot=" + slot + ">";
    }
}
