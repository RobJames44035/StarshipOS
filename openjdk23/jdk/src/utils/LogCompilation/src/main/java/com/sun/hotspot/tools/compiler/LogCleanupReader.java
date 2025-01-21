/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package com.sun.hotspot.tools.compiler;

import java.io.*;
import java.util.regex.*;

/**
 * This class is a filter class to deal with malformed XML that used
 * to be produced by the JVM when generating LogCompilation.  In 1.6
 * and later releases it shouldn't be required.
 */
class LogCleanupReader extends Reader {

    private Reader reader;

    private char[] buffer = new char[4096];

    private int bufferCount;

    private int bufferOffset;

    private char[] line = new char[1024];

    private int index;

    private int length;

    private char[] one = new char[1];

    LogCleanupReader(Reader r) {
        reader = r;
    }

    private static final Matcher duplicateCompileID = Pattern.compile(".+ compile_id='[0-9]+'.*( compile_id='[0-9]+)").matcher("");
    private static final Matcher compilerName = Pattern.compile("' (C[12]) compile_id=").matcher("");
    private static final Matcher destroyVM = Pattern.compile("'(destroy_vm)/").matcher("");

    /**
     * The log cleanup takes place in this method. If any of the three patterns
     * ({@link #duplicateCompileID}, {@link #compilerName}, {@link #destroyVM})
     * match, that indicates a problem in the log. The cleanup is performed by
     * correcting the input line and writing it back into the {@link #line}
     * buffer.
     */
    private void fill() throws IOException {
        rawFill();
        if (length != -1) {
            boolean changed = false;
            String s = new String(line, 0, length);

            compilerName.reset(s);
            if (compilerName.find()) {
                s = s.substring(0, compilerName.start(1)) + s.substring(compilerName.end(1) + 1);
                changed = true;
            }

            duplicateCompileID.reset(s);
            if (duplicateCompileID.lookingAt()) {
                s = s.substring(0, duplicateCompileID.start(1)) + s.substring(duplicateCompileID.end(1) + 1);
                changed = true;
            }

            destroyVM.reset(s);
            if (destroyVM.find()) {
                s = s.substring(0, destroyVM.start(1)) + s.substring(destroyVM.end(1));
                changed = true;
            }

            if (changed) {
                s.getChars(0, s.length(), line, 0);
                length = s.length();
            }
        }
    }

    private void rawFill() throws IOException {
        if (bufferCount == -1) {
            length = -1;
            return;
        }

        int i = 0;
        boolean fillNonEOL = true;
        outer:
        while (true) {
            if (fillNonEOL) {
                int p;
                for (p = bufferOffset; p < bufferCount; p++) {
                    char c = buffer[p];
                    if (c == '\r' || c == '\n') {
                        bufferOffset = p;
                        fillNonEOL = false;
                        continue outer;
                    }
                    if (i >= line.length) {
                        // copy and enlarge the line array
                        char[] newLine = new char[line.length * 2];
                        System.arraycopy(line, 0, newLine, 0, line.length);
                        line = newLine;
                    }
                    line[i++] = c;
                }
                bufferOffset = p;
            } else {
                int p;
                for (p = bufferOffset; p < bufferCount; p++) {
                    char c = buffer[p];
                    if (c != '\r' && c != '\n') {
                        bufferOffset = p;
                        length = i;
                        index = 0;
                        return;
                    }
                    line[i++] = c;
                }
                bufferOffset = p;
            }
            if (bufferCount == -1) {
                if (i == 0) {
                    length = -1;
                } else {
                    length = i;
                }
                index = 0;
                return;
            }
            if (bufferOffset != bufferCount) {
                System.out.println(bufferOffset);
                System.out.println(bufferCount);
                throw new InternalError("how did we get here");
            }
            // load more data and try again.
            bufferCount = reader.read(buffer, 0, buffer.length);
            bufferOffset = 0;
        }
    }

    public int read() throws java.io.IOException {
        read(one, 0, 1);
        return one[0];
    }

    public int read(char[] buffer) throws java.io.IOException {
        return read(buffer, 0, buffer.length);
    }

    public int read(char[] b, int off, int len) throws java.io.IOException {
        if (length == -1) {
            return -1;
        }

        if (index == length) {
            fill();
            if (length == -1) {
                return -1;
            }
        }
        int n = Math.min(length - index, Math.min(b.length - off, len));
        // System.out.printf("%d %d %d %d %d\n", index, length, off, len, n);
        System.arraycopy(line, index, b, off, n);
        index += n;
        return n;
    }

    public long skip(long n) throws java.io.IOException {
        long result = n;
        while (n-- > 0) read();
        return result;
    }

    public boolean ready() throws java.io.IOException {
        return reader.ready() || (line != null && length > 0);
    }

    public boolean markSupported() {
        return false;
    }

    public void mark(int unused) throws java.io.IOException {
        throw new UnsupportedOperationException("mark not supported");
    }

    public void reset() throws java.io.IOException {
        reader.reset();
        line = null;
        index = 0;
    }

    public void close() throws java.io.IOException {
        reader.close();
        line = null;
        index = 0;
    }
}
