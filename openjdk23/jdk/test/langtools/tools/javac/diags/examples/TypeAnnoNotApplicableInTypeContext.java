/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

// key: compiler.err.annotation.type.not.applicable.to.type

class TypeAnnoNotApplicableInTypeContext<T> {
    @interface A { }
    TypeAnnoNotApplicableInTypeContext<@A String> m;
}
