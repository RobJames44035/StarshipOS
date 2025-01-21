/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple AttachingConnector without default arguments used by
 * nsk/jdi/PlugConnectors/MultiConnectors/plugMultiConnect006 test
 */

package nsk.jdi.PlugConnectors.MultiConnectors.plugMultiConnect006.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import java.util.*;

public class PlugAttachConnector006_01 extends PlugConnectors implements AttachingConnector {

    static String plugAttachConnectorName
        = "PlugAttachConnector006_01_Name";
    static String plugAttachConnectorDescription
        = "PlugAttachConnector006_01_Description";
    static Transport plugAttachConnectorTransport
        = new PlugConnectorsTransport("PlugAttachConnector006_01_Transport");
    static Map<String, Connector.Argument> plugAttachConnectorDefaultArguments
        = new HashMap<String, Connector.Argument>();


    public PlugAttachConnector006_01() {

        super(plugAttachConnectorName,
            plugAttachConnectorDescription,
            plugAttachConnectorTransport,
            plugAttachConnectorDefaultArguments);
    }

} // end of PlugAttachConnector006_01 class
