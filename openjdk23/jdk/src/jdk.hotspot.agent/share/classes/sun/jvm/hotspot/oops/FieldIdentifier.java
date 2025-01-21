/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.oops;

import java.io.*;

// A FieldIdentifier describes a field in an Oop with a name
public class FieldIdentifier {

  public String getName() { return ""; }

  public void printOn(PrintStream tty) {
    tty.print(" - " + getName() + ":\t");
  }

};
