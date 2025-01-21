/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger;

/** Simple wrapper class for name and process ID information.
    Hopefully this will be generic enough to be portable. */

public class ProcessInfo {
  public ProcessInfo(String name, int pid) {
    this.name = name;
    this.pid = pid;
  }

  public String getName() {
    return name;
  }

  public int getPid() {
    return pid;
  }

  private String name;
  private int pid;
}
