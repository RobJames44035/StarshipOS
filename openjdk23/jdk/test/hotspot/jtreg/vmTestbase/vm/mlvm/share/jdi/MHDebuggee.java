/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.share.jdi;

import vm.mlvm.share.jpda.SDE_MHDebuggeeBase;

public class MHDebuggee extends SDE_MHDebuggeeBase {

    public static void main(String... args) { launch(new ArgumentHandler(args)); }

}
