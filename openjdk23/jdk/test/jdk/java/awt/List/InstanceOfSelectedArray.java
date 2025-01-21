/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
  @test
  @bug 4322321
  @summary tests that List.getSelectedIndexes() doesn't return reference to internal array
  @key headful
  @run main InstanceOfSelectedArray
*/

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.List;

public class InstanceOfSelectedArray {
     List testList;
     Frame frame;
     int[] selected;

     public static void main(String[] args) throws Exception {
         InstanceOfSelectedArray test = new InstanceOfSelectedArray();
         test.start();
     }

     public void start () throws Exception {
         try {
             EventQueue.invokeAndWait(() -> {
                 testList = new List();
                 frame = new Frame("InstanceOfSelectedArrayTest");
                 testList.addItem("First");
                 testList.addItem("Second");
                 testList.addItem("Third");

                 frame.add(testList);
                 frame.setLayout(new FlowLayout());
                 frame.setSize(300, 200);
                 frame.setLocationRelativeTo(null);
                 frame.setVisible(true);

                 testList.select(2);

                 selected = testList.getSelectedIndexes();
                 selected[0] = 0;
                 selected = testList.getSelectedIndexes();

                 if (selected[0] == 0) {
                     System.out.println("List returned the reference to internal array.");
                     System.out.println("Test FAILED");
                     throw new RuntimeException("Test FAILED");
                 }
             });

             System.out.println("List returned a clone of its internal array.");
             System.out.println("Test PASSED");
         } finally {
             EventQueue.invokeAndWait(() -> {
                 if (frame != null) {
                     frame.dispose();
                 }
             });
         }
     }
}
