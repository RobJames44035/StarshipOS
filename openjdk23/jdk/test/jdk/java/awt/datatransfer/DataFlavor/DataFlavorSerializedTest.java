/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
  @test
  @bug 4174020
  @summary DataFlavor.isMimeTypeSerializedObject works wrong
  @author prs@sparc.spb.su: area=
  @modules java.datatransfer
  @run main DataFlavorSerializedTest
*/

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

public class DataFlavorSerializedTest {
     public static boolean finished = false;
     static DataFlavor df = null;

     public static void main(String[] args) throws Exception {
         df = new DataFlavor("application/x-java-serialized-object;class=java.io.Serializable");
         boolean fl = df.isMimeTypeSerializedObject();
         finished = true;
         if (!fl)
             throw new RuntimeException("Test FAILED");
     }
}
