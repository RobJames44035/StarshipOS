/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

public interface DebugDirectoryEntry {
  /** A reserved field intended to be used for flags, set to zero for
      now. */
  public int getCharacteristics();

  /** Time and date the debug data was created. */
  public int getTimeDateStamp();

  /** Major version number of the debug data format. */
  public short getMajorVersion();

  /** Minor version number of the debug data format. */
  public short getMinorVersion();

  /** Format of debugging information: this field enables support of
      multiple debuggers. See
      {@link sun.jvm.hotspot.debugger.win32.coff.DebugTypes}. */
  public int getType();

  /** Size of the debug data (not including the debug directory itself). */
  public int getSizeOfData();

  /** Address of the debug data when loaded, relative to the image base. */
  public int getAddressOfRawData();

  /** File pointer to the debug data. */
  public int getPointerToRawData();

  /** If this debug directory entry is of type
      IMAGE_DEBUG_TYPE_CODEVIEW (see
      {@link sun.jvm.hotspot.debugger.win32.coff.DebugTypes}), returns
      the contents as a DebugVC50 object; otherwise, returns null. */
  public DebugVC50 getDebugVC50();

  /** Placeholder */
  public byte getRawDataByte(int i);
}
