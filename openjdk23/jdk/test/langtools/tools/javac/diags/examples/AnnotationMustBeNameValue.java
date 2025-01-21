/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.annotation.value.must.be.name.value
// key: compiler.err.cant.resolve

@interface Anno {
    String name() default "anon";
    String address() default "here";
}

@Anno(name == "fred", address = "there")
class X { }
