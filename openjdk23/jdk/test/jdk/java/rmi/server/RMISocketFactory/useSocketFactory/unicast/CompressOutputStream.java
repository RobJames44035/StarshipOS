/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 */

import java.io.*;

class CompressOutputStream extends FilterOutputStream
    implements CompressConstants
{

    public CompressOutputStream(OutputStream out) {
        super(out);
    }

    // buffer of 6-bit codes to pack into next 32-bit word
    int buf[] = new int[5];

    // number of valid codes pending in buffer
    int bufPos = 0;

    public void write(int b) throws IOException {
        b &= 0xFF;                      // force argument to a byte

        int pos = codeTable.indexOf((char)b);
        if (pos != -1)
            writeCode(BASE + pos);
        else {
            writeCode(RAW);
            writeCode(b >> 4);
            writeCode(b & 0xF);
        }
    }

    public void write(byte b[], int off, int len) throws IOException {
        /*
         * This is quite an inefficient implementation, because it has to
         * call the other write method for every byte in the array.  It
         * could be optimized for performance by doing all the processing
         * in this method.
         */
        for (int i = 0; i < len; i++)
            write(b[off + i]);
    }

    public void flush() throws IOException {
        while (bufPos > 0)
            writeCode(NOP);
    }

    private void writeCode(int c) throws IOException {
        buf[bufPos++] = c;
        if (bufPos == 5) {      // write next word when we have 5 codes
            int pack = (buf[0] << 24) | (buf[1] << 18) | (buf[2] << 12) |
                       (buf[3] << 6) | buf[4];
            out.write((pack >>> 24) & 0xFF);
            out.write((pack >>> 16) & 0xFF);
            out.write((pack >>> 8)  & 0xFF);
            out.write((pack >>> 0)  & 0xFF);
            bufPos = 0;
        }
    }
}
