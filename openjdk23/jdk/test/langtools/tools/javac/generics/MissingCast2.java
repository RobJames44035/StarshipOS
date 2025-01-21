/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8043741
 * @summary VerifyError due to missing checkcast
 * @author srikanth
 *
 * @compile  MissingCast2.java
 * @run main MissingCast2
 */

import java.util.*;

public class MissingCast2 {
  public static void main(String[] args) {
          new E();
  }
}

class S<T> {
    T t;
}

class C {
    class I { };
}

class E extends S<C> {
    {
        t = new C();
        t.new I() { };
    }
};
