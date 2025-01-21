/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package javax.swing.plaf;

import javax.swing.InputMap;


/**
 * A subclass of javax.swing.InputMap that implements UIResource.
 * UI classes which provide a InputMap should use this class.
 *
 * @author Scott Violet
 * @since 1.3
 */
@SuppressWarnings("serial") // Same-version serialization only
public class InputMapUIResource extends InputMap implements UIResource {
    /**
     * Constructs an {@code InputMapUIResource}.
     */
    public InputMapUIResource() {
    }
}
