/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package sun.jvm.hotspot.runtime;

import java.io.*;

import sun.jvm.hotspot.debugger.Address;

public class AttachListenerThread extends JavaThread {

  public AttachListenerThread (Address addr) {
    super(addr);
  }

  public boolean isJavaThread() { return false; }

  public boolean isAttachListenerThread() { return true; }

}
