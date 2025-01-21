/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

// key: compiler.misc.not.a.functional.intf.1
// key: compiler.err.prob.found.req
// key: compiler.misc.incompatible.abstracts

class NotAnInterfaceComponent {
    Object o = (String & Runnable) ()-> { };
}
