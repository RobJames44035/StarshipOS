/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 * @test
 * @bug 4230690
 * @summary An anonyous class in an interface is allowed.
 *
 * @run clean AnonClsInIntf I
 * @run compile AnonClsInIntf.java
 */

// The test fails if the class can not compile.

interface AnonClsInIntf {
    I i = new I()
    {
    };
}

interface I { }
