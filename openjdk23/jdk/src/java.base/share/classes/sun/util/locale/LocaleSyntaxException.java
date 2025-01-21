/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.util.locale;

public class LocaleSyntaxException extends Exception {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    private final int index;

    public LocaleSyntaxException(String msg) {
        this(msg, 0);
    }

    public LocaleSyntaxException(String msg, int errorIndex) {
        super(msg);
        index = errorIndex;
    }

    public int getErrorIndex() {
        return index;
    }
}
