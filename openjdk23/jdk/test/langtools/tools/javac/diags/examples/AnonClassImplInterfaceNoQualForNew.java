/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.anon.class.impl.intf.no.qual.for.new

class AnonClassImplInterfaceNoQualForNew {
    interface Intf {}
    AnonClassImplInterfaceNoQualForNew x;

    Object o = x.new Intf() { };
}
