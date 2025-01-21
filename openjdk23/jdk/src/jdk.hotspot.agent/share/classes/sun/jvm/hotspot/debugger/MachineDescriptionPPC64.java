/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package sun.jvm.hotspot.debugger;

public class MachineDescriptionPPC64 extends MachineDescriptionTwosComplement implements MachineDescription {
  public long getAddressSize() {
    return 8;
  }

  public boolean isLP64() {
    return true;
  }

  public boolean isBigEndian() {
    return "big".equals(System.getProperty("sun.cpu.endian"));
  }
}
