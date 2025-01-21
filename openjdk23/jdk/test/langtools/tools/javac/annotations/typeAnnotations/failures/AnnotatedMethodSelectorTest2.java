/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class AnnotatedMethodSelectorTest2<T> {
    @interface A {}
    class Inner {}
    static public void main(String... args) {
        new AnnotatedMethodSelectorTest2<@A String>() {
            java.util.@A List l;
        }.hashCode();
    }
}
