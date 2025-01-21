/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.anon.class.impl.intf.no.typeargs

class AnonClassInterfaceNoArgs {
    Runnable r = new<Integer> Runnable() {
        public void run() { }
    };
}
