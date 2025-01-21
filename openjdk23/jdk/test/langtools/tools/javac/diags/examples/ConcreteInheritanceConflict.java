/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.concrete.inheritance.conflict

class Base<T> {
    void m(T t) {  }
    void m(String s) { }
}

class ConcreteInheritanceConflict extends Base<String> { }
