/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4856978
 * @summary generics: crash when using class in bound defined after usage
 * @author gafter
 *
 * @compile  Crash01.java
 */

public class Crash01<A extends TestClass1 & IA> {
  public A value;
  public void testit(){
    value.testClass();
  }
}

class TestClass1{ public void testClass(){} }
interface IA{}
