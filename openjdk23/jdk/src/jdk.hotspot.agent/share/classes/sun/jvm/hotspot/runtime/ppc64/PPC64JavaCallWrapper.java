/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package sun.jvm.hotspot.runtime.ppc64;

import java.util.*;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.types.*;
import sun.jvm.hotspot.runtime.*;

public class PPC64JavaCallWrapper extends JavaCallWrapper {

  public PPC64JavaCallWrapper(Address addr) {
    super(addr);
  }

  public Address getLastJavaFP() {
    return null;
  }

}
