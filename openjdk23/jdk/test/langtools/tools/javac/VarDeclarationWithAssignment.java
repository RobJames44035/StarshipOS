/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4076729
 * @summary The statement "int j = (j = BLAH);" did not compile correctly in
 *          some contexts.
 * @author turnidge
 *
 * @compile VarDeclarationWithAssignment.java
 * @run main/othervm -verify VarDeclarationWithAssignment
 */

public
class VarDeclarationWithAssignment {
  int x;

  public static void main(String[] args) {
    for (int i = 0; i < 1; i++);
    VarDeclarationWithAssignment c = new VarDeclarationWithAssignment();
    int j = (j=4);
    c.x = 11;
   }
}
