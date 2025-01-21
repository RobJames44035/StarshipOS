/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4030421 4095716
 * @summary Verify that compiler can resolve local class names.
 * @author William Maddox (maddox)
 *
 * @clean LocalClasses_1a LocalClasses_1b
 * @compile LocalClasses_1.java
 */

class LocalClasses_1a {
    class Inner {
        void f() {
            class Local { }
            new Local();
        }
    }
}

class LocalClasses_1b {
    void f() {
        class Inner { }
        new Object() {
            {
                new Inner();
            }
        };
    }
}

class LocalClasses_1c {
    Object f() {
        class Local { }
        new Object() {
            int x;
            Local y;
            Local g() {
                return new Local();
            }
        };
        return new Local();
    }
}

class LocalClasses_1d {
    void f() {
        class Local { }
        class Inner {
            Local z;
        }
    }
}
