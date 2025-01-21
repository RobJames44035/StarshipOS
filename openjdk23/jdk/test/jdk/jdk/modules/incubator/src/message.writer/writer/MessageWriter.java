/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package writer;

import java.io.PrintStream;
import java.util.Locale;

public class MessageWriter {
    public static void writeOn(String message, PrintStream out) {
        String newMesssage = converter.MessageConverter.toUpperCase(message);
        if (!newMesssage.equals(message.toUpperCase(Locale.ROOT)))
            throw new RuntimeException("Expected " + message.toUpperCase(Locale.ROOT)
                                       + ", got " + newMesssage );

        out.println(newMesssage);
    }
}
