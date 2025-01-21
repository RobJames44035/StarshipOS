/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.bytecodes;

import com.sun.hotspot.igv.data.services.InputGraphProvider;
import com.sun.hotspot.igv.util.LookupHistory;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

/**
 *
 * @author Thomas Wuerthinger
 */
public final class SelectBytecodesAction extends CookieAction {

    @Override
    protected void performAction(Node[] activatedNodes) {
        SelectBytecodesCookie c = activatedNodes[0].getCookie(SelectBytecodesCookie.class);
        InputGraphProvider p = LookupHistory.getLast(InputGraphProvider.class);
        if (p != null) {
            p.clearSelectedNodes();
            p.addSelectedNodes(c.getNodes(), true);
            p.centerSelectedNodes();
        }
    }

    @Override
    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(SelectBytecodesAction.class, "CTL_SelectBytecodesAction");
    }

    @Override
    protected Class[] cookieClasses() {
        return new Class[]{
            SelectBytecodesCookie.class
        };
    }

    @Override
    protected void initialize() {
        super.initialize();
        putValue("noIconInMenu", Boolean.TRUE);
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

