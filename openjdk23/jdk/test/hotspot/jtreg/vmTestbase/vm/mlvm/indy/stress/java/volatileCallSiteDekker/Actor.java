/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package vm.mlvm.indy.stress.java.volatileCallSiteDekker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VolatileCallSite;
import vm.mlvm.share.DekkerTest;
import vm.mlvm.indy.share.CallSiteDekkerActor;

public class Actor extends CallSiteDekkerActor {
    public Actor() {
        super(new VolatileCallSite(MH_FALSE), new VolatileCallSite(MH_FALSE));
    }
}
