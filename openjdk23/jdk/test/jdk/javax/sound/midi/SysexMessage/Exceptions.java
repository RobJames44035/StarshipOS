/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import static javax.sound.midi.SysexMessage.SYSTEM_EXCLUSIVE;

/**
 * @test
 * @bug 8221445
 * @summary Checks exceptions thrown by javax.sound.midi.SysexMessage class
 */
public final class Exceptions {

    public static void main(final String[] args) throws Exception {
        testInvalidMidiDataException();
        testIndexOutOfBoundsException();
        testNullPointerException();
    }

    private static void testInvalidMidiDataException() {
        try {
            // data should conatins a status byte
            new SysexMessage(new byte[0], 0);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final InvalidMidiDataException ignored) {
            // ok
        }
        try {
            // length is zero, no space for the status byte
            new SysexMessage(new byte[]{(byte) (SYSTEM_EXCLUSIVE)}, 0);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final InvalidMidiDataException ignored) {
            // ok
        }
        try {
            // status should conatins a status byte (0xF0 or 0xF7)
            new SysexMessage(0, new byte[0], 2);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final InvalidMidiDataException ignored) {
            // ok
        }
        SysexMessage sysexMessage = new SysexMessage();
        try {
            // data should conatins a status byte
            sysexMessage.setMessage(new byte[0], 0);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final InvalidMidiDataException ignored) {
            // ok
        }
        try {
            // length is zero, no space for the status byte
            sysexMessage.setMessage(new byte[]{(byte) (SYSTEM_EXCLUSIVE)}, 0);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final InvalidMidiDataException ignored) {
            // ok
        }
        try {
            // data should conatins a status byte (0xF0 or 0xF7)
            sysexMessage.setMessage(new byte[]{0}, 0);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final InvalidMidiDataException ignored) {
            // ok
        }
        try {
            // status should conatins a status byte (0xF0 or 0xF7)
            sysexMessage.setMessage(0, new byte[0], 0);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final InvalidMidiDataException ignored) {
            // ok
        }
    }

    private static void testIndexOutOfBoundsException() throws Exception {
        // length is bigger than data
        try {
            new SysexMessage(new byte[]{(byte) (0xF0 & 0xFF)}, 2);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final IndexOutOfBoundsException ignored) {
            // ok
        }
        try {
            new SysexMessage(0xF0, new byte[0], 2);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final IndexOutOfBoundsException ignored) {
            // ok
        }
        SysexMessage sysexMessage = new SysexMessage();
        try {
            sysexMessage.setMessage(new byte[]{(byte) (0xF0 & 0xFF)}, 2);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final IndexOutOfBoundsException ignored) {
            // ok
        }
        try {
            sysexMessage.setMessage(0xF0, new byte[0], 2);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final IndexOutOfBoundsException ignored) {
            // ok
        }

        // length is negative
        try {
            new SysexMessage(new byte[]{(byte) (0xF0 & 0xFF)}, -1);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final IndexOutOfBoundsException ignored) {
            // ok
        }
        try {
            new SysexMessage(0xF0, new byte[0], -1);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final IndexOutOfBoundsException ignored) {
            // ok
        }
        sysexMessage = new SysexMessage();
        try {
            sysexMessage.setMessage(new byte[]{(byte) (0xF0 & 0xFF)}, -1);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final IndexOutOfBoundsException ignored) {
            // ok
        }
        try {
            sysexMessage.setMessage(0xF0, new byte[0], -1);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final IndexOutOfBoundsException ignored) {
            // ok
        }
    }

    private static void testNullPointerException() throws Exception {
        try {
            new SysexMessage(null, 0);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final NullPointerException ignored) {
            // ok
        }
        try {
            new SysexMessage(SYSTEM_EXCLUSIVE, null, 2);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final NullPointerException ignored) {
            // ok
        }
        SysexMessage sysexMessage = new SysexMessage();
        try {
            sysexMessage.setMessage(null, 0);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final NullPointerException ignored) {
            // ok
        }
        sysexMessage = new SysexMessage();
        try {
            sysexMessage.setMessage(SYSTEM_EXCLUSIVE, null, 2);
            throw new RuntimeException("Expected exception is not thrown");
        } catch (final NullPointerException ignored) {
            // ok
        }
    }
}
