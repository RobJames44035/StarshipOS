/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package compiler.c2.irTests.scalarReplacement;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8267265
 * @summary Tests that Escape Analysis and Scalar Replacement is able to handle some simple cases.
 * @library /test/lib /
 * @run driver compiler.c2.irTests.scalarReplacement.ScalarReplacementTests
 */
public class ScalarReplacementTests {
    private class Person {
        private String name;
        private int age;

        public Person(Person p) {
            this.name = p.getName();
            this.age = p.getAge();
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
    }

    public static void main(String[] args) {
        TestFramework.run();
    }

    @Test
    @Arguments(values = Argument.RANDOM_EACH)
    @IR(failOn = {IRNode.CALL, IRNode.LOAD, IRNode.STORE, IRNode.FIELD_ACCESS, IRNode.ALLOC})
    public String stringConstant(int age) {
        Person p = new Person("Java", age);
        return p.getName();
    }

    @Test
    @Arguments(values = Argument.RANDOM_EACH)
    @IR(failOn = {IRNode.CALL, IRNode.LOAD, IRNode.STORE, IRNode.FIELD_ACCESS, IRNode.ALLOC})
    public int intConstant(int age) {
        Person p = new Person("Java", age);
        return p.getAge();
    }

    @Test
    @Arguments(values = Argument.RANDOM_EACH)
    @IR(failOn = {IRNode.CALL, IRNode.LOAD, IRNode.STORE, IRNode.FIELD_ACCESS, IRNode.ALLOC})
    public String nestedStringConstant(int age) {
        Person p1 = new Person("Java", age);
        Person p2 = new Person(p1);
        return p2.getName();
    }

    @Test
    @Arguments(values = Argument.RANDOM_EACH)
    @IR(failOn = {IRNode.CALL, IRNode.LOAD, IRNode.STORE, IRNode.FIELD_ACCESS, IRNode.ALLOC})
    public int nestedIntConstant(int age) {
        Person p1 = new Person("Java", age);
        Person p2 = new Person(p1);
        return p2.getAge();
    }

    @Test
    @Arguments(values = {Argument.RANDOM_EACH, Argument.RANDOM_EACH})
    @IR(failOn = {IRNode.CALL, IRNode.LOAD, IRNode.STORE, IRNode.FIELD_ACCESS, IRNode.ALLOC})
    public int nestedConstants(int age1, int age2) {
        Person p = new Person(
                        new Person("Java", age1).getName(),
                        new Person("Java", age2).getAge());
        return p.getAge();
    }
}
