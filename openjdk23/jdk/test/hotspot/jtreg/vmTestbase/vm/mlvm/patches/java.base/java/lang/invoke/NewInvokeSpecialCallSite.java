/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package java.lang.invoke;

/**
 * This is a CallSite, which constructor can be used as bootstrap method (via REF_newInvokeSpecial reference kind).
 *
 * This call site always calls MethodHandle set with {@link #setMH(MethodHandle)} method (no lookup by method name/type is performed!)
 * <p>Since we can't extend the java.lang.invoke.CallSite from package other than java.lang.invoke, we use system package name
 * for this class.
 */
public final class NewInvokeSpecialCallSite extends CallSite {

    private static MethodHandle mh;

    /**
     * Sets method handle, which will be used for CallSite target
     */
    public static void setMH(MethodHandle newMH) {
        mh = newMH;
    }

    /**
     * Constructs a CallSite. This constructor has special signature, which can be used for bootstrap method target
     * of REF_newInvokeSpecial reference kind.
     * @param lookup Ignored.
     * @param name Ignored.
     * @param type Ignored.
     */
    public NewInvokeSpecialCallSite(MethodHandles.Lookup lookup, String name, MethodType type) {
        super(mh);
    }

    /**
     * This method is no-op. Use {@link #setMH(MethodHandle)} for setting the target
     */
    public final void setTarget(MethodHandle newMH) {
        // No-op
    }

    /**
     * Always returns method handle set with {@link #setMH(MethodHandle)} method
     */
    public final MethodHandle getTarget() {
        return mh;
    }

    public final MethodHandle dynamicInvoker() {
        return makeDynamicInvoker();
    }
}
