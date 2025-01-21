/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.prefs.Preferences;

/**
 * @test
 * @bug 6203576 4700020 7197662 8217777
 * @summary checks if the output of exportSubtree() is identical to
 *          the output from previous release.
 * @run main/othervm -Djava.util.prefs.userRoot=. ExportSubtree
 */
public class ExportSubtree {
    private static final String LS = System.getProperty("line.separator");

    private static final String IMPORT_PREFS =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<!DOCTYPE preferences SYSTEM \"http://java.sun.com/dtd/preferences.dtd\">" +
        "<preferences EXTERNAL_XML_VERSION=\"1.0\">" +
        "  <root type=\"user\">" +
        "    <map>" +
        "      <entry key=\"key1\" value=\"value1\"/>" +
        "    </map>" +
        "    <node name=\"testExportSubtree\">" +
        "      <map>" +
        "        <entry key=\"key2\" value=\"value2\"/>" +
        "      </map>" +
        "      <node name=\"test\">" +
        "        <map>" +
        "          <entry key=\"key3\" value=\"value3\"/>" +
        "        </map>" +
        "      </node>" +
        "    </node>" +
        "  </root>" +
        "</preferences>";

    private static final String EXPECTED_RESULT =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + LS +
        "<!DOCTYPE preferences SYSTEM \"http://java.sun.com/dtd/preferences.dtd\">" + LS +
        "<preferences EXTERNAL_XML_VERSION=\"1.0\">" + LS +
        "  <root type=\"user\">" + LS +
        "    <map/>" + LS +
        "    <node name=\"testExportSubtree\">" + LS +
        "      <map>" + LS +
        "        <entry key=\"key2\" value=\"value2\"/>" + LS +
        "      </map>" + LS +
        "      <node name=\"test\">" + LS +
        "        <map>" + LS +
        "          <entry key=\"key3\" value=\"value3\"/>" + LS +
        "        </map>" + LS +
        "      </node>" + LS +
        "    </node>" + LS +
        "  </root>" + LS +
        "</preferences>" + LS;

    public static void main(String[] args) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(IMPORT_PREFS.getBytes("utf-8"));
        Preferences.importPreferences(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Preferences.userRoot().node("testExportSubtree").exportSubtree(baos);
        Preferences.userRoot().node("testExportSubtree").removeNode();
        if (!EXPECTED_RESULT.equals(baos.toString())) {
            String errMsg = "Preferences::exportSubtree did not yield the expected result.";
            System.out.println(errMsg + LS +
                               "Actual:" + LS +
                               baos + LS +
                               "Expected:" + LS +
                               EXPECTED_RESULT);
            throw new RuntimeException(errMsg);
        }
    }
}
