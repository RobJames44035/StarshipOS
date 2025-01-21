/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger;

import java.io.*;

/** An abstraction which represents a seekable data source.
    RandomAccessFile can be trivially mapped to this; in addition, we
    can support an adapter for addresses, so we can parse DLLs
    directly out of the remote process's address space.  This class is
    used by the Windows COFF and Posix ELF implementations. */

public interface DataSource {
  public byte  readByte()       throws IOException;
  public short readShort()      throws IOException;
  public int   readInt()        throws IOException;
  public long  readLong()       throws IOException;
  public int   read(byte[] b)   throws IOException;
  public void  seek(long pos)   throws IOException;
  public long  getFilePointer() throws IOException;
  public void  close()          throws IOException;
}
