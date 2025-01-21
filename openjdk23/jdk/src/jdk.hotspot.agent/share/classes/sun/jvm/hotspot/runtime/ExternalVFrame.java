/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.runtime;

import java.io.*;

public class ExternalVFrame extends VFrame {
  private boolean mayBeImprecise;

  /** Package-internal constructor */
  ExternalVFrame(Frame fr, RegisterMap regMap, JavaThread thread, boolean mayBeImprecise) {
    super(fr, regMap, thread);

    this.mayBeImprecise = mayBeImprecise;
  }

  public void print() {
    printOn(System.out);
  }

  public void printOn(PrintStream tty) {
    getFrame().printValueOn(tty);
  }

  public void printValue() {
    printValueOn(System.out);
  }

  public void printValueOn(PrintStream tty) {
    super.printOn(tty);
  }

  public boolean mayBeImpreciseDbg() {
    return mayBeImprecise;
  }
}
