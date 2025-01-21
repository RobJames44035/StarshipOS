/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

public interface WindowsNTSubsystem {
  /** Unknown subsystem. */
  public short IMAGE_SUBSYSTEM_UNKNOWN = (short) 0;

  /** Used for device drivers and native Windows NT processes. */
  public short IMAGE_SUBSYSTEM_NATIVE = (short) 1;

  /** Image runs in the Windows graphical user interface (GUI) subsystem. */
  public short IMAGE_SUBSYSTEM_WINDOWS_GUI = (short) 2;

  /** Image runs in the Windows character subsystem. */
  public short IMAGE_SUBSYSTEM_WINDOWS_CUI = (short) 3;

  /** Image runs in the Posix character subsystem. */
  public short IMAGE_SUBSYSTEM_POSIX_CUI = (short) 7;

  /** Image runs on Windows CE. */
  public short IMAGE_SUBSYSTEM_WINDOWS_CE_GUI = (short) 9;

  /** Image is an EFI application. */
  public short IMAGE_SUBSYSTEM_EFI_APPLICATION = (short) 10;

  /** Image is an EFI driver that provides boot services. */
  public short IMAGE_SUBSYSTEM_EFI_BOOT_SERVICE_DRIVER = (short) 11;

  /** Image is an EFI driver that provides runtime services. */
  public short IMAGE_SUBSYSTEM_EFI_RUNTIME_DRIVER = (short) 12;
}
