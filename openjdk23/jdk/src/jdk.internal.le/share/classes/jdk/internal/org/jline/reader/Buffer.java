/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package jdk.internal.org.jline.reader;

public interface Buffer {

    /*
     * Read access
     */

    int cursor();

    int atChar(int i);

    int length();

    int currChar();

    int prevChar();

    int nextChar();

    /*
     * Movement
     */

    boolean cursor(int position);

    int move(int num);

    boolean up();

    boolean down();

    boolean moveXY(int dx, int dy);

    /*
     * Modification
     */

    boolean clear();

    boolean currChar(int c);

    void write(int c);

    void write(int c, boolean overTyping);

    void write(CharSequence str);

    void write(CharSequence str, boolean overTyping);

    boolean backspace();

    int backspace(int num);

    boolean delete();

    int delete(int num);

    /*
     * String
     */

    String substring(int start);

    String substring(int start, int end);

    String upToCursor();

    String toString();

    /*
     * Copy
     */

    Buffer copy();

    void copyFrom(Buffer buffer);

    /**
     * Clear any internal buffer.
     */
    void zeroOut();
}
