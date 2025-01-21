/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.prob.found.req
// key: compiler.misc.not.def.access.class.intf.cant.access
// key: compiler.misc.invalid.mref

class NotDefAccessClassIntfCantAccessFragment {

    private class Private {
        void m() { }
    }

    Private getPrivate() { return new Private(); }
}

class NotDefAccessClassIntfCantAccessFragmentTest {

    interface SAM {
        void m();
    }

    static void test() {
        SAM s = new NotDefAccessClassIntfCantAccessFragment().getPrivate()::m;
    }
}
