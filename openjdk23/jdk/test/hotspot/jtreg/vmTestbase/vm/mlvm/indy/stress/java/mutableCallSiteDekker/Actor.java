/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package vm.mlvm.indy.stress.java.mutableCallSiteDekker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MutableCallSite;
import vm.mlvm.share.DekkerTest;
import vm.mlvm.indy.share.CallSiteDekkerActor;

public class Actor extends CallSiteDekkerActor {
    public Actor() {
        super(new MutableCallSite(MH_FALSE), new MutableCallSite(MH_FALSE));
    }

}
