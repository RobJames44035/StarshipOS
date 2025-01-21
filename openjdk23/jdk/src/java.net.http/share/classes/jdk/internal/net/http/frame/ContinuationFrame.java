/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.internal.net.http.frame;

import java.nio.ByteBuffer;
import java.util.List;

public class ContinuationFrame extends HeaderFrame {

    public static final int TYPE = 0x9;

    public ContinuationFrame(int streamid, int flags, List<ByteBuffer> headerBlocks) {
        super(streamid, flags, headerBlocks);
    }

    public ContinuationFrame(int streamid, ByteBuffer headersBlock) {
        this(streamid, 0, List.of(headersBlock));
    }

    @Override
    public int type() {
        return TYPE;
    }

    @Override
    int length() {
        return headerLength;
    }

}
