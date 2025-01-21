/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package com.sun.hotspot.igv.coordinator.actions;

import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

/**
 *
 * @author Thomas Wuerthinger
 */
public final class DiffGraphAction extends CookieAction {

    @Override
    protected void performAction(Node[] activatedNodes) {
        DiffGraphCookie c = activatedNodes[0].getCookie(DiffGraphCookie.class);
        assert c != null;
        c.openDiff();
    }

    @Override
    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        boolean b = super.enable(activatedNodes);
        if (b) {
            assert activatedNodes.length == 1;
            DiffGraphCookie c = activatedNodes[0].getCookie(DiffGraphCookie.class);
            assert c != null;
            return c.isPossible();
        }

        return false;
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(DiffGraphAction.class, "CTL_DiffGraphAction");
    }

    @Override
    protected Class<?>[] cookieClasses() {
        return new Class<?>[]{
            DiffGraphCookie.class
        };
    }

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/coordinator/images/diff.png";
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}

