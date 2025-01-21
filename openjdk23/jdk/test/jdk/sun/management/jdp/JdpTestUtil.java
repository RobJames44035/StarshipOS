/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility methods for parsing raw JDP packets.
 *
 * @author Alex Schenkman
 */
public class JdpTestUtil {

    static final int HEADER_SIZE = 4 + 2;   // magic + protocol version

    /**
     * Reads two bytes, starting at the given position,
     * and converts them into an int.
     *
     * @param data
     * @param pos
     * @return
     */
    static int decode2ByteInt(byte[] data, int pos) {
        return (((data[pos] & 0xFF) << 8) | (data[pos + 1] & 0xFF));
    }

    /**
     * Reads four bytes, starting at the given position,
     * and converts them into an int.
     *
     * @param data
     * @param pos
     * @return
     */
    static int decode4ByteInt(byte[] data, int pos) {
        int result = data[pos + 3] & 0xFF;
        result = result | ((data[pos + 2] & 0xFF) << 8);
        result = result | ((data[pos + 1] & 0xFF) << 16);
        result = result | ((data[pos] & 0xFF) << 24);
        return result;
    }

    /**
     * Reads an entry from the given byte array, starting at the given position.
     * This is an internal function used by @see readRawPayload(byte[] rawPayload, int size).
     * <p/>
     * The format of an entry is:
     * 2 bytes with the size of the following string.
     * n bytes of characters.
     *
     * @param data
     * @param pos
     * @return
     * @throws UnsupportedEncodingException
     */
    static String decodeEntry(byte[] data, int pos)
            throws UnsupportedEncodingException {

        int size = JdpTestUtil.decode2ByteInt(data, pos);
        pos = pos + 2;
        byte[] raw = Arrays.copyOfRange(data, pos, pos + size);
        return new String(raw, "UTF-8");
    }

    /**
     * Builds a Map with the payload, from the raw data.
     *
     * @param rawData
     * @return
     * @throws UnsupportedEncodingException
     */
    static Map<String, String> readPayload(byte[] rawData)
            throws UnsupportedEncodingException {

        int totalSize = rawData.length;
        int payloadSize = totalSize - HEADER_SIZE;
        byte[] rawPayload = Arrays.copyOfRange(rawData, HEADER_SIZE, HEADER_SIZE + payloadSize);
        Map<String, String> payload = readRawPayload(rawPayload, payloadSize);
        return payload;
    }

    /**
     * Builds a map from the payload's raw data.
     * This is an internal function used by @see readPayload(byte[] rawData)
     *
     * @param rawPayload
     * @param size
     * @return
     * @throws UnsupportedEncodingException
     */
    static Map<String, String> readRawPayload(byte[] rawPayload, int size)
            throws UnsupportedEncodingException {

        String key, value;
        Map<String, String> payload = new HashMap<String, String>();

        for (int pos = 0; pos < size; ) {
            key = decodeEntry(rawPayload, pos);
            pos = pos + 2 + key.length();
            value = decodeEntry(rawPayload, pos);
            pos = pos + 2 + value.length();

            payload.put(key, value);
        }
        return payload;
    }

    static void enableConsoleLogging(Logger log, Level level) throws SecurityException {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(level);
        log.addHandler(handler);
        log.setLevel(level);
    }

}
