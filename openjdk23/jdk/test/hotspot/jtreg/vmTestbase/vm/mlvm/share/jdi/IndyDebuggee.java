/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.share.jdi;

import vm.mlvm.share.jpda.INDIFY_SDE_DebuggeeBase;

public class IndyDebuggee extends INDIFY_SDE_DebuggeeBase {

    public static void main(String... args) throws Throwable { launch(new ArgumentHandler(args)); }

}
