/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4853816
 * @summary Test StringBuffer.substring(int)
 */

public class Substring {
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Guten Morgen!");
        if (buffer.substring(0).length() != 13)
            throw new RuntimeException();
    }
}
