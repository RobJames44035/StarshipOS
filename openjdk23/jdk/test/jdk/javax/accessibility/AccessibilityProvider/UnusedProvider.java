/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import javax.accessibility.AccessibilityProvider;

public final class UnusedProvider extends AccessibilityProvider {

    private static final String name = "UnusedProvider";

    public String getName() {
        return name;
    }

    public void activate() {
        // Write to log to indicate activate was called.
        try (PrintWriter writer = new PrintWriter("UnusedProvider.txt")) {
            writer.println("UnusedProvider-activated");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
