/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.lwawt.macosx;

import sun.awt.Mutex;
import sun.awt.datatransfer.ToolkitThreadBlockedHandler;

final class CToolkitThreadBlockedHandler extends Mutex implements ToolkitThreadBlockedHandler {
    private long awtRunLoopMediator = 0;
    private final boolean processEvents;

    CToolkitThreadBlockedHandler() {
        super();
        this.processEvents = true;
    }

    public void enter() {
        if (!isOwned()) {
            throw new IllegalMonitorStateException();
        }
        awtRunLoopMediator = LWCToolkit.createAWTRunLoopMediator();
        unlock();
        LWCToolkit.doAWTRunLoop(awtRunLoopMediator, processEvents);
        lock();
    }

    public void exit() {
        if (!isOwned()) {
            throw new IllegalMonitorStateException();
        }
        LWCToolkit.stopAWTRunLoop(awtRunLoopMediator);
        awtRunLoopMediator = 0;
    }
}
