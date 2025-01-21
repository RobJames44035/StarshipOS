/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4350863 4358130
 * @summary Verify that name of superclass constructor exception can be
 * resolved correctly when instantiating a subclass.
 * @author maddox
 *
 * @compile SuperclassConstructorException.java
 */

class Superclass {
  public Superclass() throws java.io.IOException {}
}

class Subclass extends SuperclassConstructorException {}

class SuperclassConstructorException {

  public static void main(String args[]) {
    try {
      Object x = new Superclass(){};
      Object y = new Subclass();
    } catch (java.io.IOException e) {};
  }
}
