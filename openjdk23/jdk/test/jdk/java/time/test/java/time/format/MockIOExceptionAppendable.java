/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package test.java.time.format;

import java.io.IOException;

/**
 * Mock Appendable that throws IOException.
 */
public class MockIOExceptionAppendable implements Appendable {

    public Appendable append(CharSequence csq) throws IOException {
        throw new IOException();
    }

    public Appendable append(char c) throws IOException {
        throw new IOException();
    }

    public Appendable append(CharSequence csq, int start, int end)
            throws IOException {
        throw new IOException();
    }

}
