/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
  @test
  @bug 4458016
  @summary KeyboardFocusManager.get[Property|Vetoable]ChangeListeners throw NPE
  @key headful
  @run main PropertySupportNPETest
*/

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;

public class PropertySupportNPETest {
     public static void main(String[] args) throws Exception {
         EventQueue.invokeAndWait(() -> {
             KeyboardFocusManager kfm =
                     KeyboardFocusManager.getCurrentKeyboardFocusManager();
             kfm.getVetoableChangeListeners();
             kfm.getVetoableChangeListeners("");
             kfm.getPropertyChangeListeners();
             kfm.getPropertyChangeListeners("");
         });
     }
 }
