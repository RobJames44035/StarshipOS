/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.lwawt.macosx;

import sun.lwawt.LWComponentPeer;
import sun.lwawt.PlatformDropTarget;

import java.awt.*;
import java.awt.dnd.DropTarget;


final class CDropTarget implements PlatformDropTarget {
    private long fNativeDropTarget;

    CDropTarget(DropTarget dropTarget, Component component, LWComponentPeer<?, ?> peer) {
        long nativePeer = CPlatformWindow.getNativeViewPtr(peer.getPlatformWindow());
        if (nativePeer == 0L) return; // Unsupported for a window without a native view (plugin)

        // Create native dragging destination:
        fNativeDropTarget = createNativeDropTarget(dropTarget, component, nativePeer);
        if (fNativeDropTarget == 0) {
            throw new IllegalStateException("CDropTarget.createNativeDropTarget() failed.");
        }
    }

    @Override
    public void dispose() {
        if (fNativeDropTarget != 0) {
            releaseNativeDropTarget(fNativeDropTarget);
            fNativeDropTarget = 0;
        }
    }

    protected native long createNativeDropTarget(DropTarget dropTarget,
                                                 Component component,
                                                 long nativePeer);
    protected native void releaseNativeDropTarget(long nativeDropTarget);
}
