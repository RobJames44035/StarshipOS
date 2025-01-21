/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6758673
  @summary Tests that windows are removed from owner's child windows list
  @modules java.desktop/java.awt:open
  @author art: area=awt.toplevel
  @run main/othervm -Xmx128m OwnedWindowsLeak
*/

import java.awt.Frame;
import java.awt.Window;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Vector;

public class OwnedWindowsLeak
{
    public static void main(String[] args) throws Exception
    {
        Frame owner = new Frame("F");

        // First, create many windows
        Vector<WeakReference<Window>> children =
            new Vector<WeakReference<Window>>();
        for (int i = 0; i < 1000; i++)
        {
            Window child = new Window(owner);
            child.setName("window_" +  i);
            children.add(new WeakReference<Window>(child));
        }

        // Second, make sure all the memory is allocated
        Vector garbage = new Vector();
        while (true)
        {
            try
            {
                garbage.add(new byte[1000]);
            }
            catch (OutOfMemoryError e)
            {
                break;
            }
        }
        garbage = null;

        // Third, make sure all the weak references are null
        for (WeakReference<Window> ref : children)
        {
            while (ref.get() != null) {
                System.out.println("ref.get() = " + ref.get());
                System.gc();
                Thread.sleep(1000);
            }
        }

        // Fourth, make sure owner's children list contains no elements
        Field f = Window.class.getDeclaredField("ownedWindowList");
        f.setAccessible(true);
        Vector ownersChildren = (Vector)f.get(owner);
        while (ownersChildren.size() > 0)
        {
            System.out.println("ownersChildren = " + ownersChildren);
            System.gc();
            Thread.sleep(1000);
        }

        // Test passed
        System.out.println("Test PASSED");

        owner.dispose();
    }
}
