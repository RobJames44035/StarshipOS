/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.DataAmount;
import jdk.jfr.Name;
import jdk.jfr.internal.Type;
import jdk.jfr.internal.MirrorEvent;

@Name(Type.EVENT_NAME_PREFIX + "FileRead")
@Label("File Read")
@Category("Java Application")
@Description("Reading data from a file")
@StackFilter({"java.io.FileInputStream", "java.io.RandomAccessFile", "sun.nio.ch.FileChannelImpl"})
public final class FileReadEvent extends MirrorEvent {

    @Label("Path")
    @Description("Full path of the file, or N/A if a file descriptor was used to create the stream, for example System.in")
    public String path;

    @Label("Bytes Read")
    @Description("Number of bytes read from the file (possibly 0)")
    @DataAmount
    public long bytesRead;

    @Label("End of File")
    @Description("If end of file was reached")
    public boolean endOfFile;

}
