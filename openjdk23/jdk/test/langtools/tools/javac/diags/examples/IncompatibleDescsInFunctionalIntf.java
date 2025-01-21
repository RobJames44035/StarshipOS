/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.types.incompatible
// key: compiler.misc.incompatible.diff.ret
// key: compiler.err.prob.found.req
// key: compiler.misc.incompatible.descs.in.functional.intf
// key: compiler.misc.descriptor
// key: compiler.misc.descriptor.throws

class IncompatibleDescsInFunctionalIntf {
    interface A {
        Integer m(String i) throws Exception;
    }

    interface B {
        String m(String i);
    }

    interface SAM extends A,B { }

    SAM s = x-> { };
}
