/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package javax.swing.event;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A listener implementing all the methods in both the {@code MouseListener} and
 * {@code MouseMotionListener} interfaces.
 *
 * @see MouseInputAdapter
 * @author Philip Milne
 */

public interface MouseInputListener extends MouseListener, MouseMotionListener {
}
