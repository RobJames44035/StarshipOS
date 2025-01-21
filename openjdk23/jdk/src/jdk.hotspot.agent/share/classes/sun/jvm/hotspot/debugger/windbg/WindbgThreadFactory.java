/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.windbg;

import sun.jvm.hotspot.debugger.*;

/** An interface used only internally by the WindbgDebugger to be able to
    create platform-specific Thread objects */

public interface WindbgThreadFactory {
  public ThreadProxy createThreadWrapper(Address threadIdentifierAddr);
  public ThreadProxy createThreadWrapper(long id);
}
