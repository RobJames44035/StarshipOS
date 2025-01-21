/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.security.krb5.internal.rcache;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

/**
 * The class represents an old style replay cache entry. It is only used in
 * a dfl file.
 *
 * @author Sun/Oracle
 * @author Yanni Zhang
 */
public class AuthTime {
    final int ctime;
    final int cusec;
    final String client;
    final String server;

    /**
     * Constructs an <code>AuthTime</code>.
     */
    public AuthTime(String client, String server,
            int ctime, int cusec) {
        this.ctime = ctime;
        this.cusec = cusec;
        this.client = client;
        this.server = server;
    }

    @Override
    public String toString() {
        return String.format("%d/%06d/----/%s", ctime, cusec, client);
    }

    // Methods used when saved in a dfl file. See DflCache.java

    /**
     * Reads an LC style string from a channel, which is a int32 length
     * plus a UTF-8 encoded string possibly ends with \0.
     * @throws IOException if there is a format error
     * @throws BufferUnderflowException if goes beyond the end
     */
    private static String readStringWithLength(SeekableByteChannel chan)
            throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.nativeOrder());
        chan.read(bb);
        bb.flip();
        int len = bb.getInt();
        if (len > 1024) {
            // Memory attack? The string should be fairly short.
            throw new IOException("Invalid string length");
        }
        bb = ByteBuffer.allocate(len);
        if (chan.read(bb) != len) {
            throw new IOException("Not enough string");
        }
        byte[] data = bb.array();
        return (data[len-1] == 0)?
                new String(data, 0, len-1, StandardCharsets.UTF_8):
                new String(data, StandardCharsets.UTF_8);
    }

    /**
     * Reads an AuthTime or AuthTimeWithHash object from a channel.
     * @throws IOException if there is a format error
     * @throws BufferUnderflowException if goes beyond the end
     */
    public static AuthTime readFrom(SeekableByteChannel chan)
            throws IOException {
        String client = readStringWithLength(chan);
        String server = readStringWithLength(chan);
        ByteBuffer bb = ByteBuffer.allocate(8);
        chan.read(bb);
        bb.order(ByteOrder.nativeOrder());
        int cusec = bb.getInt(0);
        int ctime = bb.getInt(4);
        if (client.isEmpty()) {
            StringTokenizer st = new StringTokenizer(server, " :");
            if (st.countTokens() != 6) {
                throw new IOException("Incorrect rcache style");
            }
            String hashAlg = st.nextToken();
            String hash = st.nextToken();
            st.nextToken();
            client = st.nextToken();
            st.nextToken();
            server = st.nextToken();
            return new AuthTimeWithHash(
                    client, server, ctime, cusec, hashAlg, hash);
        } else {
            return new AuthTime(
                    client, server, ctime, cusec);
        }
    }

    /**
     * Encodes to be used in a dfl file
     */
    protected byte[] encode0(String cstring, String sstring) {
        byte[] c = cstring.getBytes(StandardCharsets.UTF_8);
        byte[] s = sstring.getBytes(StandardCharsets.UTF_8);
        byte[] zero = new byte[1];
        int len = 4 + c.length + 1 + 4 + s.length + 1 + 4 + 4;
        ByteBuffer bb = ByteBuffer.allocate(len)
                .order(ByteOrder.nativeOrder());
        bb.putInt(c.length+1).put(c).put(zero)
                .putInt(s.length+1).put(s).put(zero)
                .putInt(cusec).putInt(ctime);
        return bb.array();
    }

    /**
     * Encodes to be used in a dfl file
     * @param withHash useless here
     */
    public byte[] encode(boolean withHash) {
        return encode0(client, server);
    }
}
