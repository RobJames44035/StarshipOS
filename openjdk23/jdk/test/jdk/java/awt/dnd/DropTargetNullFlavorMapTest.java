/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import java.awt.datatransfer.SystemFlavorMap;
import java.awt.dnd.DropTarget;

/*
  @test
  @bug 4785476
  @summary tests that DropTarget.setFlavorMap(null) works properly
  @key headful
  @run main DropTargetNullFlavorMapTest
*/
public class DropTargetNullFlavorMapTest {

    public static void main(String[] args) {
        DropTargetNullFlavorMapTest test = new DropTargetNullFlavorMapTest();
        test.init();
    }

    public void init() {
        final DropTarget dropTarget = new DropTarget();

        if (!SystemFlavorMap.getDefaultFlavorMap().equals(dropTarget.getFlavorMap())) {
            System.err.println("Default flavor map: " + SystemFlavorMap.getDefaultFlavorMap());
            System.err.println("DropTarget's flavor map: " + dropTarget.getFlavorMap());
            throw new RuntimeException("Incorrect flavor map.");
        }

        Thread.currentThread().setContextClassLoader(new ClassLoader() {});

        dropTarget.setFlavorMap(null);

        if (!SystemFlavorMap.getDefaultFlavorMap().equals(dropTarget.getFlavorMap())) {
            System.err.println("Default flavor map: " + SystemFlavorMap.getDefaultFlavorMap());
            System.err.println("DropTarget's flavor map: " + dropTarget.getFlavorMap());
            throw new RuntimeException("Incorrect flavor map.");
        }
    }
}
