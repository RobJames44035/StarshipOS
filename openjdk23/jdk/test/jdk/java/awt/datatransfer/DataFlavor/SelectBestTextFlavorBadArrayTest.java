/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
  @test
  @bug 4386360
  @summary tests that DataFlavor.selectBestTextFlavor() returns null when passed
           is a null array or an empty array or an array which doesn't contain
           a text flavor in a supported encoding.
  @author das@sparc.spb.su area=datatransfer
  @modules java.datatransfer
  @run main SelectBestTextFlavorBadArrayTest
*/

import java.awt.datatransfer.DataFlavor;
import java.util.Stack;
import java.util.stream.Collectors;

public class SelectBestTextFlavorBadArrayTest {

    public static void main(String[] args) {
        final String[] failureMessages = {
            "DataFlavor.selectBestTextFlavor(null) doesn't return null.",
            "DataFlavor.selectBestTextFlavor() doesn't return null for an empty array.",
            "DataFlavor.selectBestTextFlavor() shouldn't return flavor in an unsupported encoding."
        };
        Stack<String> failures = new Stack<>();

        DataFlavor flavor = DataFlavor.selectBestTextFlavor(null);
        if (flavor != null) {
            failures.push(failureMessages[0]);
        }
        flavor = DataFlavor.selectBestTextFlavor(new DataFlavor[0]);
        if (flavor != null) {
            failures.push(failureMessages[1]);
        }

        try {
            flavor = DataFlavor.selectBestTextFlavor(new DataFlavor[]
                { new DataFlavor("text/plain; charset=unsupported; class=java.io.InputStream") });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            failures.push("Exception thrown: " + e.toString());
        }
        if (flavor != null) {
            failures.push(failureMessages[2]);
        }

        if (failures.size() > 0) {
            String failureReport = failures.stream().collect(Collectors.joining("\n"));
            throw new RuntimeException("TEST FAILED: \n" + failureReport);
        }
    }
}
