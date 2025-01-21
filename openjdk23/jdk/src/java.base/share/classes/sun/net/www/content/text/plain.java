/*
 * StarshipOS Copyright (c) 1994-2025. R.A. James
 */

package sun.net.www.content.text;
import java.net.*;
import java.io.InputStream;
import java.io.IOException;

/**
 * Plain text file handler.
 * @author  Steven B. Byrne
 */
public class plain extends ContentHandler {
    /**
     * Returns a PlainTextInputStream object from which data
     * can be read.
     */
    public Object getContent(URLConnection uc) {
        try {
            InputStream is = uc.getInputStream();
            return new PlainTextInputStream(uc.getInputStream());
        } catch (IOException e) {
            return "Error reading document:\n" + e.toString();
        }
    }
}
