/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */


class T6747671a {

    DeprecatedClass a1; //warn

    @SuppressWarnings("deprecation")
    DeprecatedClass a2;

    <X extends DeprecatedClass> DeprecatedClass m1(DeprecatedClass a)
            throws DeprecatedClass { return null; } //warn

    @SuppressWarnings("deprecation")
    <X extends DeprecatedClass> DeprecatedClass m2(DeprecatedClass a)
            throws DeprecatedClass { return null; }

    void test() {
        DeprecatedClass a1; //warn

        @SuppressWarnings("deprecation")
        DeprecatedClass a2;
    }
}
