/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
  @test
  @bug 4478912
  @summary tests that getNativesForFlavor()/getFlavorsForNative() return the
           same list as was set with setNativesForFlavor()/setFlavorsForNative()
  @author das@sparc.spb.su area=datatransfer
  @modules java.datatransfer
  @run main SetNativesForFlavorTest
*/

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;

public class SetNativesForFlavorTest  {

    public static void main(String[] args) throws Exception {
            final String nativeString = "NATIVE";

            final SystemFlavorMap fm =
                (SystemFlavorMap)SystemFlavorMap.getDefaultFlavorMap();

            fm.setNativesForFlavor(DataFlavor.plainTextFlavor,
                                   new String[] { nativeString });

            final java.util.List natives =
                fm.getNativesForFlavor(DataFlavor.plainTextFlavor);

            if (natives.size() != 1 || !natives.contains(nativeString)) {
                throw new RuntimeException("getNativesForFlavor() returns:" +
                                           natives);
            }

            final DataFlavor dataFlavor =
                new DataFlavor("text/unknown; class=java.lang.String");

            fm.setFlavorsForNative(nativeString, new DataFlavor[] { dataFlavor });

            final java.util.List flavors = fm.getFlavorsForNative(nativeString);

            if (flavors.size() != 1 || !flavors.contains(dataFlavor)) {
                throw new RuntimeException("getFlavorsForNative() returns:" +
                                           flavors);
            }
    }
}
