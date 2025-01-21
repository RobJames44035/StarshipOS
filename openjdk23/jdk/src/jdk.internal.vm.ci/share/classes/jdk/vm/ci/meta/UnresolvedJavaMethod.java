/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package jdk.vm.ci.meta;

/**
 * Implementation of {@link JavaMethod} for unresolved HotSpot methods.
 */
public final class UnresolvedJavaMethod implements JavaMethod {

    private final String name;
    private final Signature signature;
    protected JavaType holder;

    /**
     * The reason method resolution failed. Can be null.
     */
    private final Throwable cause;

    public UnresolvedJavaMethod(String name, Signature signature, JavaType holder, Throwable cause) {
        this.name = name;
        this.holder = holder;
        this.signature = signature;
        this.cause = cause;
    }

    public UnresolvedJavaMethod(String name, Signature signature, JavaType holder) {
        this(name, signature, holder, null);
    }

    /**
     * Gets the exception, if any, representing the reason method resolution resulted in this object.
     */
    public Throwable getCause() {
        return cause;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Signature getSignature() {
        return signature;
    }

    @Override
    public JavaType getDeclaringClass() {
        return holder;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UnresolvedJavaMethod)) {
            return false;
        }
        UnresolvedJavaMethod that = (UnresolvedJavaMethod) obj;
        return this.name.equals(that.name) && this.signature.equals(that.signature) && this.holder.equals(that.holder);
    }
}
