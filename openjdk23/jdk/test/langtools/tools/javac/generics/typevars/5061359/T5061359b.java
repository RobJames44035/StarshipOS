/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     5061359
 * @summary No error for ambiguous member of intersection
 * @compile T5061359b.java
 */

class Test<T extends Base & Intf> {
    public void foo() {
        T t = null;
        t._field = 3;         // This should not be an accessibility error
    }

}

class Base {
    int _field;
}

interface Intf {}
