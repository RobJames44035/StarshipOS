/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @bug      7062745 7157798
 * @summary  Test inheritance of same-name methods from multiple interfaces
             when the methods have compatible parameter types and return types
 * @compile  Test2.java
 */

import java.util.*;

interface A { void m(Map map); }
interface B { void m(Map<Number, String> map); }

interface AB extends A, B {} //paramter type: Map<Number, String>

interface C<K, V> { List<V> getList(Map<K, V> map); }
interface D { ArrayList getList(Map map); }

interface CD<K, V> extends C<K, V>, D {} //paramter type: Map<K, V>

interface E<T> { T get(List<?> list); }
interface F<T> { T get(List list); }

interface EF<T1, T2 extends T1> extends E<T1>, F<T2> {} //parameter type: List<?>

class Test2 {

    //compatible parameter types:
    void test(AB ab) {
        ab.m(new HashMap<Number, String>());
    }

    //compatible parameter types and return types:
    void testRaw(CD cd) { //return type: ArrayList
        ArrayList al = cd.getList(new HashMap());
    }

    <K, V> void testGeneric(CD<K, V> cd) { //return type: List<V>
        V v = cd.getList(new HashMap<K, V>()).get(0);
    }

    void test(CD<Number, String> cd) { //return type: List<String>
        String s = cd.getList(new HashMap<Number, String>()).get(0);
    }

    void test(EF<Number, Integer> ef) { //return type: Number
        Number n = ef.get(new ArrayList<Integer>());
    }

    <T, U extends T> void testGeneric(EF<T, U> ef) { //return type: T
        T t = ef.get(new ArrayList<U>());
    }
}
