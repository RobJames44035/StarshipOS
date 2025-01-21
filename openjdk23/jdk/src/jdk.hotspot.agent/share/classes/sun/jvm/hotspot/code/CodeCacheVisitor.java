/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package sun.jvm.hotspot.code;

import java.util.*;
import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.memory.*;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.types.*;
import sun.jvm.hotspot.utilities.*;

public interface CodeCacheVisitor {

  void prologue(Address start, Address end);

  void visit(CodeBlob blob);

  void epilogue();

}
