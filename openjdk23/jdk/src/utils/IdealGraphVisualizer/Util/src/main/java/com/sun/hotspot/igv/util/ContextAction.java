/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */
package com.sun.hotspot.igv.util;

import com.sun.hotspot.igv.data.ChangedListener;
import java.awt.EventQueue;
import org.openide.util.*;
import org.openide.util.actions.CallableSystemAction;

/**
 *
 * @author Thomas Wuerthinger
 */
public abstract class ContextAction<T> extends CallableSystemAction implements LookupListener, ContextAwareAction, ChangedListener<T> {

    private T t;

    private Lookup.Result<T> result = null;

    public ContextAction() {
        this(Utilities.actionsGlobalContext());
    }

    public ContextAction(Lookup context) {
        init(context);
    }

    private void init(Lookup context) {
        result = context.lookupResult(contextClass());
        result.addLookupListener(this);
        resultChanged(null);
    }

    @Override
    public void resultChanged(LookupEvent e) {
        if (result.allItems().size() != 0) {
            update(result.allInstances().iterator().next());
        } else {
            update(null);
        }
    }

    @Override
    public void performAction() {
        final T t = result.allInstances().iterator().next();

        // Ensure it's AWT event thread
        EventQueue.invokeLater(() -> performAction(t));
    }

    public void update(T t) {
        if (t == null) {
            setEnabled(false);
        } else {
            setEnabled(isEnabled(t));
        }
        if (this.t != t) {
            if (this.t != null) {
                removeContextListener(this.t);
            }
            this.t = t;
            if (this.t != null) {
                addContextListener(this.t);
            }
        }
    }

    @Override
    public void changed(T t) {
        update(t);
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    public abstract boolean isEnabled(T context);

    public abstract Class<T> contextClass();

    public abstract void performAction(T t);

    public abstract void addContextListener(T t);

    public abstract void removeContextListener(T t);
}
