/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 4619564 7197662
 * @summary XMl Comments in Preferences File lead to ClassCastException
 * @run main/othervm -Djava.util.prefs.userRoot=. CommentsInXml
 * @author kladko
 */

import java.io.*;
import java.util.prefs.*;

public class CommentsInXml {

    public static void main(String[] argv) throws Exception {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bos.write(new String(
            "<!DOCTYPE preferences SYSTEM                          " +
            "\"http://java.sun.com/dtd/preferences.dtd\">          " +
            "<preferences EXTERNAL_XML_VERSION=\"1.0\">            " +
            "  <root type=\"user\">                                " +
            "    <map>                                             " +
            "    </map>                                            " +
            "    <node name=\"hlrAgent\"> <!-- HLR Agent -->       " +
            "      <map>                                           " +
            "        <entry key=\"agentName\" value=\"HLRAgent\" />" +
            "      </map>                                          " +
            "    </node>                                           " +
            "  </root>                                             " +
            "</preferences>                                        "
        ).getBytes());

        Preferences ur = Preferences.userRoot();
        ur.importPreferences(new ByteArrayInputStream(bos.toByteArray()));
        ur.node("hlrAgent").removeNode(); // clean
    }
}
