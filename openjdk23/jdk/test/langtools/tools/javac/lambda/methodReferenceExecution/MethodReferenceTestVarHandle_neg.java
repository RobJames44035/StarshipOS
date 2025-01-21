/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.invoke.*;
import java.util.*;

public class MethodReferenceTestVarHandle_neg {

  interface Setter {
      int apply(int[] arr, int idx, int val);
  }

  public static void meth() {
      VarHandle vh = MethodHandles.arrayElementVarHandle(int[].class);

      // Return type of Setter::apply does not match return type of VarHandle::set
      Setter f = vh::set;
  }
}
