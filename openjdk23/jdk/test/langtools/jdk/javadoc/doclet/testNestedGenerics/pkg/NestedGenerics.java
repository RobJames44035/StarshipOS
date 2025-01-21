/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package pkg;

import java.util.Map;

/** Contains {@link #foo} */
public class NestedGenerics {
  public static <A> void foo(Map<A, Map<A, A>> map) {}
}
