/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.lang.annotation.*;

/*
 * @test
 * @bug 6843077 8006775
 * @summary new type annotation location: throw clauses
 * @author Mahmood Ali
 * @compile Throws.java
 */
class DefaultUnmodified {
  void oneException() throws @A Exception {}
  void twoExceptions() throws @A RuntimeException, @A Exception {}
}

class PublicModified {
  public final void oneException(String a) throws @A Exception {}
  public final void twoExceptions(String a) throws @A RuntimeException, @A Exception {}
}

class WithValue {
  void oneException() throws @B("m") Exception {}
  void twoExceptions() throws @B(value="m") RuntimeException, @A Exception {}
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A {}
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface B { String value(); }
