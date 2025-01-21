/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package sun.jvm.hotspot.code;

import java.util.*;
import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.types.*;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

public class UpcallStub extends RuntimeBlob {

  private static CIntegerField frameDataOffsetField;
  private static AddressField lastJavaFPField;
  private static AddressField lastJavaSPField;
  private static AddressField lastJavaPCField;

  static {
    VM.registerVMInitializedObserver(new Observer() {
        public void update(Observable o, Object data) {
          initialize(VM.getVM().getTypeDataBase());
        }
      });
  }

  private static void initialize(TypeDataBase db) {
    Type type = db.lookupType("UpcallStub");
    frameDataOffsetField = type.getCIntegerField("_frame_data_offset");

    Type anchorType = db.lookupType("JavaFrameAnchor");
    lastJavaSPField = anchorType.getAddressField("_last_Java_sp");
    lastJavaPCField = anchorType.getAddressField("_last_Java_pc");

    try {
      lastJavaFPField = anchorType.getAddressField("_last_Java_fp");
    } catch (Exception e) {
      // Some platforms (e.g. PPC64) does not have this field.
      lastJavaFPField = null;
    }
  }

  public UpcallStub(Address addr) {
    super(addr);
  }

  protected Address getJavaFrameAnchor(Frame frame) {
    var frameDataOffset = frameDataOffsetField.getValue(addr);
    var frameDataAddr = frame.getUnextendedSP().addOffsetTo(frameDataOffset);
    var frameData = VMObjectFactory.newObject(FrameData.class, frameDataAddr);
    return frameData.getJavaFrameAnchor();
  }

  public Address getLastJavaSP(Frame frame) {
    return lastJavaSPField.getValue(getJavaFrameAnchor(frame));
  }

  public Address getLastJavaFP(Frame frame) {
    return lastJavaFPField == null ? null : lastJavaFPField.getValue(getJavaFrameAnchor(frame));
  }

  public Address getLastJavaPC(Frame frame) {
    return lastJavaPCField.getValue(getJavaFrameAnchor(frame));
  }

  public boolean isUpcallStub() {
    return true;
  }

  public static class FrameData extends VMObject {

    private static AddressField jfaField;

    static {
      VM.registerVMInitializedObserver(new Observer() {
        public void update(Observable o, Object data) {
          initialize(VM.getVM().getTypeDataBase());
        }
      });
    }

    private static void initialize(TypeDataBase db) {
      Type type = db.lookupType("UpcallStub::FrameData");
      jfaField = type.getAddressField("jfa");
    }

    public FrameData(Address addr) {
      super(addr);
    }

    public Address getJavaFrameAnchor() {
      return addr.addOffsetTo(jfaField.getOffset());
    }

  }

}
