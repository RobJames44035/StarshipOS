/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

package test.java.awt.regtesthelpers;

import java.awt.peer.FramePeer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.awt.Toolkit;
import java.awt.Frame;

import sun.awt.AWTAccessor;

/**
   Class with static methods using internal/proprietary API by necessity.
*/
public final class UtilInternal {

    private UtilInternal () {} // this is a helper class with static methods :)

    public static Frame createEmbeddedFrame(final Frame embedder)
        throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException,
               InstantiationException, InvocationTargetException
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        FramePeer frame_peer = AWTAccessor.getComponentAccessor()
                                          .getPeer(embedder);
        System.out.println("frame's peer = " + frame_peer);
        if ("sun.awt.windows.WToolkit".equals(tk.getClass().getName())) {
            java.awt.Helper.addExports("sun.awt.windows", UtilInternal.class.getModule());

            Class comp_peer_class =
                Class.forName("sun.awt.windows.WComponentPeer");
            System.out.println("comp peer class = " + comp_peer_class);
            Field hwnd_field = comp_peer_class.getDeclaredField("hwnd");
            hwnd_field.setAccessible(true);
            System.out.println("hwnd_field =" + hwnd_field);
            long hwnd = hwnd_field.getLong(frame_peer);
            System.out.println("hwnd = " + hwnd);

            Class clazz = Class.forName("sun.awt.windows.WEmbeddedFrame");
            Constructor constructor = clazz.getConstructor (new Class [] {Long.TYPE});
            return (Frame) constructor.newInstance (new Object[] {hwnd});
        } else if ("sun.awt.X11.XToolkit".equals(tk.getClass().getName())) {
            java.awt.Helper.addExports("sun.awt.X11", UtilInternal.class.getModule());
            Class x_base_window_class = Class.forName("sun.awt.X11.XBaseWindow");
            Method get_window = x_base_window_class.getMethod("getWindow", new Class[0]);
            System.out.println("get_window = " + get_window);
            long window = (Long) get_window.invoke(frame_peer, new Object[0]);
            System.out.println("window = " + window);
            Class clazz = Class.forName("sun.awt.X11.XEmbeddedFrame");
            Constructor constructor = clazz.getConstructor (new Class [] {Long.TYPE, Boolean.TYPE});
            return (Frame) constructor.newInstance (new Object[] {window, true});
        }

        throw new RuntimeException("Unexpected toolkit - " + tk);
    }
}
