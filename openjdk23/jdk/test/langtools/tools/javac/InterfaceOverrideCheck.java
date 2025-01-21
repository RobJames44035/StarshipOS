/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4312154
 * @summary Verify that overriding compatibility is checked in interfaces.
 * @author maddox
 *
 * @run compile/fail InterfaceOverrideCheck.java
 */

class X1 extends Throwable {}

class X2 extends Throwable {}

interface I1
{
   int f(int i, int j) throws X1;
}

interface I2 extends I1
{
   int f(int i, int j) throws X1,X2;// error: Throws clause conflict.
}
