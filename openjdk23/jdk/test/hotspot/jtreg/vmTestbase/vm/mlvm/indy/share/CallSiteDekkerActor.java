/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package vm.mlvm.indy.share;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.CallSite;
import vm.mlvm.share.DekkerTest;

/**
 * The class implements Actor for {@link vm.mlvm.share.DekkerTest}.
 * <p>
 * This Actor switches targets for CallSite objects supplied in constructor.
 * Targets are method handles, generated with MethodHandle.constant(), which return true or false.
 * CallSite.setTarget() is used for setting appropriate target.
 * CallSite.dynamicInvoker().invokeExact() is used to detect which target is currently set.
 * No synchronization is used between setter and getter (see {@link vm.mlvm.share.DekkerTest} for details).
 *
 * @see vm.mlvm.share.DekkerTest
 */
public class CallSiteDekkerActor implements DekkerTest.Actor {

    public static final MethodHandle MH_FALSE = MethodHandles.constant(Boolean.class, false);
    public static final MethodHandle MH_TRUE = MethodHandles.constant(Boolean.class, true);

    private final CallSite a;
    private final CallSite b;

    public CallSiteDekkerActor(CallSite csa, CallSite csb) {
        a = csa;
        b = csb;
    }

    @Override
    public void reset() {
        a.setTarget(MH_FALSE);
        b.setTarget(MH_FALSE);
    }

    @Override
    public boolean actorA() throws Throwable {
        a.setTarget(MH_TRUE);
        return (Boolean) b.dynamicInvoker().invokeExact();
    }

    @Override
    public boolean actorB() throws Throwable {
        b.setTarget(MH_TRUE);
        return (Boolean) a.dynamicInvoker().invokeExact();
    }
}
