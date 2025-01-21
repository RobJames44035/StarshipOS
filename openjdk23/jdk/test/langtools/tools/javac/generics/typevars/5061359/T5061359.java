/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class Test<T extends Base & Intf> {
    public void foo() {
        T t = null;
        T.Inner inner = null; // This should be an ambiguous error
    }

}

class Base {
    static class Inner {}
}

interface Intf {
    class Inner {}
}
