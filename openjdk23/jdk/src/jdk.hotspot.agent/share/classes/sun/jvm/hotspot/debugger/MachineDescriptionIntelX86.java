/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger;

public class MachineDescriptionIntelX86 extends MachineDescriptionTwosComplement implements MachineDescription {
  public long getAddressSize() {
    return 4;
  }

  public boolean isBigEndian() {
    return false;
  }

  public boolean supports32bitAlignmentOf64bitTypes() {
    return true;
  }
}
