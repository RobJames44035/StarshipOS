/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.posix.elf;

public interface ELFStringTable {
    public String get(int index);
    public int getNumStrings();
}
