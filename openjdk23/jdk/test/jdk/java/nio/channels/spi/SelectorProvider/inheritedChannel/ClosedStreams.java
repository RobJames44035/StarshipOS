/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4997227
 * @summary Calling inheritedChannel() after FileDescriptor.in was closed
 *          caused an InternalError to be thrown.
 * @build ClosedStreams
 * @run main/othervm ClosedStreams
 */

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class ClosedStreams {
    public static void main(String args[]) throws IOException {

        // close FileDescriptor.in
        (new FileInputStream(FileDescriptor.in)).close();

        // get the inherited channel
        if (System.inheritedChannel() != null) {
            throw new RuntimeException("inherited channel not null - unexpected!");
        }
    }
}
