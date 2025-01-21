/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package sun.jvm.hotspot.interpreter;

import sun.jvm.hotspot.oops.*;
import sun.jvm.hotspot.runtime.*;

public class BytecodeWithKlass extends BytecodeWithCPIndex {
  BytecodeWithKlass(Method method, int bci) {
    super(method, bci);
  }

  protected Klass getKlass() {
    return method().getConstants().getKlassAt(index());
  }

  public Symbol getClassName() {
    return method().getConstants().getKlassNameAt(index());
  }

  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(getJavaBytecodeName());
    buf.append(spaces);
    buf.append('#');
    buf.append(index());
    buf.append(spaces);
    buf.append("[Class ");
    buf.append(getClassName().asString().replace('/', '.'));
    buf.append(']');
    if (code() != javaCode()) {
       buf.append(spaces);
       buf.append('[');
       buf.append(getBytecodeName());
       buf.append(']');
    }
    return buf.toString();
  }
}
