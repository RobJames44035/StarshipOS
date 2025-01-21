/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.ByteArrayInputStream;

class TestingInputStream extends ByteArrayInputStream {

    TestingInputStream() {
        super(new byte[0]);
    }

    void setInput(String s) {
        this.buf = s.getBytes();
        this.pos = 0;
        this.count = buf.length;
    }

}
