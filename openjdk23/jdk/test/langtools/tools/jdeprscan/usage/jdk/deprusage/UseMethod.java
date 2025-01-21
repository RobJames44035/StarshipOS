/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jdeprusage;

import jdk.deprcases.members.ExampleClass;
import jdk.deprcases.members.ExampleInterface;
import jdk.deprcases.members.ExampleSubclass;

public class UseMethod {
    static class Direct {
        void m(ExampleClass ec) {
            ec.method1();
        }
    }

    static class Inherited {
        void m(ExampleSubclass esc) {
            esc.method2();
        }
    }

    static class InheritedDefault {
        void m(ExampleSubclass esc) {
            esc.defaultMethod();
        }
    }

    static class InterfaceDirect {
        void m(ExampleInterface ei) {
            ei.interfaceMethod1();
        }
    }

    static class InterfaceDefault {
        void m(ExampleInterface ei) {
            ei.defaultMethod();
        }
    }

    static class ClassStatic {
        void m() {
            ExampleClass.staticmethod1();
        }
    }

    static class InterfaceStatic {
        void m() {
            ExampleInterface.staticmethod2();
        }
    }

    static class SuperFromSubclass extends ExampleClass {
        void m() {
            super.method1();
        }
    }

    static class InheritedFromSubclass extends ExampleClass {
        void m() {
            method1();
        }
    }

    static class Constructor {
        Object m() {
            return new ExampleClass(true);
        }
    }

    static class ConstructorFromSubclass extends ExampleClass {
        public ConstructorFromSubclass() {
            super(true);
        }
    }

    abstract static class InheritedInterfaceDefault extends ExampleSubclass {
        void m() {
            defaultMethod();
        }
    }

    abstract static class InheritedInterface extends ExampleSubclass {
        void m() {
            interfaceMethod1();
        }
    }

    static class OverrideClassMethod extends ExampleClass {
        @Override
        public void method1() { }
    }

    abstract static class OverrideInterfaceMethod implements ExampleInterface {
        @Override
        public void interfaceMethod1() { }
    }

    abstract static class OverrideDefaultMethod implements ExampleInterface {
        @Override
        public void defaultMethod() { }
    }
}
