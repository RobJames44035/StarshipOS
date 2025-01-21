/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package sun.font;

@SuppressWarnings("serial") // JDK-implementation class
public class FontScalerException extends Exception {
    public FontScalerException() {
      super("Font scaler encountered runtime problem.");
    }

    public FontScalerException(String reason) {
      super (reason);
    }
}
