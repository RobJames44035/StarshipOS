/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

public interface AuxSectionDefinitionsRecord extends AuxSymbolRecord {
  /** Size of section data; same as Size of Raw Data in the section
      header. */
  public int getLength();

  /** Number of relocation entries for the section. */
  public short getNumberOfRelocations();

  /** Number of line-number entries for the section. */
  public short getNumberOfLineNumbers();

  /** Checksum for communal data. Applicable if the
      IMAGE_SCN_LNK_COMDAT flag is set in the section header. */
  public int getCheckSum();

  /** One-based index into the Section Table for the associated
      section; used when the COMDAT Selection setting is 5. */
  public short getNumber();

  /** COMDAT selection number. Applicable if the section is a COMDAT
      section. See {@link
      sun.jvm.hotspot.debugger.win32.coff.COMDATSelectionTypes}. */
  public byte getSelection();
}
