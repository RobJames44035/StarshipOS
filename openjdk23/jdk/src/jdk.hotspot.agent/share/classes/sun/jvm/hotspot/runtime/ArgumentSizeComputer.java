/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.runtime;

import sun.jvm.hotspot.oops.*;

/** Specialized SignatureIterator: Used to compute the argument size. */

public class ArgumentSizeComputer extends SignatureInfo {
  protected void set(int size, int type)        { if (!isReturnType()) this.size += size; }
  public ArgumentSizeComputer(Symbol signature) { super(signature); }
}
