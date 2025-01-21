/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.utilities;

import java.util.*;

import sun.jvm.hotspot.oops.*;

public class FindObjectByType implements HeapVisitor {
  private Klass type;
  private List<Oop> results = new ArrayList<>();

  public FindObjectByType(Klass type) {
    this.type = type;
  }

  /** Returns a List of Oops */
  public List<Oop> getResults() {
    return results;
  }

  public void prologue(long size) {}
  public void epilogue()          {}

  public boolean doObj(Oop obj) {
    if (obj.getKlass().equals(type)) {
      results.add(obj);
    }
        return false;
  }
}
