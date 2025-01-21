/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */
package com.sun.hotspot.tools.compiler;

import java.io.PrintStream;

/**
 * In a compilation log, represent the event of making a given compiled method
 * not-entrant, e.g., during an OSR compilation.
 */
class MakeNotEntrantEvent extends BasicLogEvent {

    /**
     * Denote whether the method is marked as a zombie, i.e., no further
     * activations exist.
     */
    private final boolean zombie;

    /**
     * The method in question.
     */
    private NMethod nmethod;

    /**
     * The compilation level.
     */
    private String level;

    /**
     * The compile kind.
     */
    private String compileKind;

    MakeNotEntrantEvent(double s, String i, boolean z, NMethod nm) {
        super(s, i);
        zombie = z;
        nmethod = nm;
    }

    public NMethod getNMethod() {
        return nmethod;
    }

    public void print(PrintStream stream, boolean printID) {
        if (isZombie()) {
            stream.printf("%s make_zombie\n", getId());
        } else {
            stream.printf("%s make_not_entrant\n", getId());
        }
    }

    public boolean isZombie() {
        return zombie;
    }

  /**
   * @return the level
   */
  public String getLevel() {
      return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(String level) {
      this.level = level;
  }

    /**
   * @return the compileKind
   */
  public String getCompileKind() {
      return compileKind;
  }

  /**
   * @param compileKind the compileKind to set
   */
  public void setCompileKind(String compileKind) {
      this.compileKind = compileKind;
  }

  public String toString() {
      return "MakeNotEntrantEvent zombie:" + isZombie() + ", id:" + getId() + ", kind:" + getCompileKind();
  }

}
