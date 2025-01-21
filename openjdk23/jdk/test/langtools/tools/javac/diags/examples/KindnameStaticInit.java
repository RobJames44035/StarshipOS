/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// key: compiler.err.already.defined.in.clinit
// key: compiler.misc.kindname.static.init
// key: compiler.misc.kindname.class
// key: compiler.misc.kindname.variable
// key: compiler.misc.count.error
// key: compiler.err.error
// run: backdoor

class KindnameStaticInit {
    static {
        int i;
        int i;
    }
}
