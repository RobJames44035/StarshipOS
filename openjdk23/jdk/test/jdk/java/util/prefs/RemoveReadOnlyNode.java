/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */


/* @test
 * @bug 6178148 7197662
 * @summary check if wrong exception gets thrown if one of the child
 *          nodes is readonly on underlying filesystem when node is
 *          being removed.
 * @run main/othervm -Djava.util.prefs.userRoot=. RemoveReadOnlyNode
 */

import java.io.*;
import java.util.prefs.*;

public class RemoveReadOnlyNode {
    public static void main(String[] args) throws Exception {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows"))
            return;
        Preferences root = Preferences.userRoot();
        Preferences node1 = root.node("node1");
        Preferences node1A = node1.node("node1A");
        Preferences node1B = node1.node("node1B");
        node1B.put("mykey", "myvalue");
        node1.flush();
        String node1BDirName = System.getProperty("user.home")
            + "/.java/.userPrefs"
            + "/node1/node1B";
        File node1BDir = new File(node1BDirName);
        node1BDir.setReadOnly();
        try {
            node1.removeNode();
        }
        catch (BackingStoreException ex) {
            //expected exception
        } finally {
            Runtime.getRuntime().exec("chmod 755 " + node1BDirName).waitFor();
            try {
                node1.removeNode();
            } catch (Exception e) {}
        }
    }
}
