/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package java.nio.channels.spi;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import jdk.internal.invoke.MhUtil;
import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelectorImpl;

/**
 * Base implementation class for selection keys.
 *
 * <p> This class tracks the validity of the key and implements cancellation.
 *
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class AbstractSelectionKey
    extends SelectionKey
{
    private static final VarHandle INVALID = MhUtil.findVarHandle(
            MethodHandles.lookup(), "invalid", boolean.class);

    /**
     * Initializes a new instance of this class.
     */
    protected AbstractSelectionKey() { }

    private volatile boolean invalid;

    public final boolean isValid() {
        return !invalid;
    }

    void invalidate() {                                 // package-private
        invalid = true;
    }

    /**
     * Cancels this key.
     *
     * <p> If this key has not yet been cancelled then it is added to its
     * selector's cancelled-key set while synchronized on that set.  </p>
     */
    public final void cancel() {
        boolean changed = (boolean) INVALID.compareAndSet(this, false, true);
        if (changed) {
            Selector sel = selector();
            if (sel instanceof SelectorImpl) {
                // queue cancelled key directly
                ((SelectorImpl) sel).cancel((SelectionKeyImpl) this);
            } else {
                ((AbstractSelector) sel).cancel(this);
            }
        }
    }
}
