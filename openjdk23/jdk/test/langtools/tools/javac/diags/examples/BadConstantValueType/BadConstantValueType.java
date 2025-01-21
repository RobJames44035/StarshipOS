/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

// key: compiler.misc.bad.constant.value.type
// key: compiler.misc.bad.class.file.header
// key: compiler.err.cant.access
// options: -processor CreateBadClassFile

/* The annotation processor will create an invalid classfile with a static
 * final field of type java.lang.Object having ConstantValue attribute with
 * a String value
 */
class BadConstantValueType {
    private static final String C = Test.test;
}
