/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jdeprusage;

import jdk.deprcases.members.ExampleClass;
import jdk.deprcases.members.ExampleInterface;
import jdk.deprcases.members.ExampleSubclass;

public class UseField {
    static class Direct {
        int f(ExampleClass ec) {
            return ec.field1;
        }
    }

    static class Inherited {
        int f(ExampleSubclass esc) {
            return esc.field2;
        }
    }

    static class InterfaceInherited {
        int f(ExampleSubclass esc) {
            return esc.DEP_FIELD2;
        }
    }

    static class InterfaceDirect {
        int f(ExampleInterface ei) {
            return ei.DEP_FIELD1;
        }
    }

    static class FromSubclass extends ExampleClass {
        int f() {
            return field1;
        }
    }

    static class SuperFromSubclass extends ExampleClass {
        int f() {
            return super.field1;
        }
    }

    static class StaticField {
        int f() {
            return ExampleClass.staticfield3;
        }
    }
}
