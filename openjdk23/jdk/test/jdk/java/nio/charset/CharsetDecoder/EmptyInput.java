/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
 * @bug 4400697
 * @summary Ensure that CharsetDecoder.decode throws BUE on empty input
 */

import java.nio.*;
import java.nio.charset.*;


public class EmptyInput {

    public static void main(String[] args) throws Exception {
        ByteBuffer bb = ByteBuffer.allocate(10);
        bb.flip();
        CharsetDecoder cd = Charset.forName("US-ASCII").newDecoder();
        try {
            cd.decode(bb, CharBuffer.allocate(10), true).throwException();
        } catch (BufferUnderflowException x) {
            System.err.println("BufferUnderflowException thrown as expected");
            return;
        }
        throw new Exception("BufferUnderflowException not thrown");
    }

}
