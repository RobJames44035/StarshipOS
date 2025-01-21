/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

// key: compiler.err.enum.annotation.must.be.enum.constant

enum E {
    A,
    B;

    public static final E e = A;
}

@interface Anno {
    E value();
}

@Anno(E.e)
class EnumAnnoValueMustBeEnumConstant { }
