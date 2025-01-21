/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.compiler;

import sun.jvm.hotspot.debugger.*;

/** Adaptation of the oop visitation mechanism to Java. */

public interface OopMapVisitor {
  public void visitOopLocation(Address oopAddr);
  public void visitDerivedOopLocation(Address baseOopAddr, Address derivedOopAddr);
  public void visitNarrowOopLocation(Address narrowOopAddr);
}
