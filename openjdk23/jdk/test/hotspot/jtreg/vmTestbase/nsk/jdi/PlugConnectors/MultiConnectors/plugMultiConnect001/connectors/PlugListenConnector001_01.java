/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple ListeningConnector without default arguments used by
 * nsk/jdi/PlugConnectors/MultiConnectors/plugMultiConnect001 test
 */

package nsk.jdi.PlugConnectors.MultiConnectors.plugMultiConnect001.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import java.util.*;

public class PlugListenConnector001_01 extends PlugConnectors implements ListeningConnector {

    static String plugListenConnectorName
        = "PlugListenConnector001_01_Name";
    static String plugListenConnectorDescription
        = "PlugListenConnector001_01_Description";
    static Transport plugListenConnectorTransport
        = new PlugConnectorsTransport("PlugListenConnector001_01_Transport");
    static Map<String, Connector.Argument> plugListenConnectorDefaultArguments
        = new HashMap<String, Connector.Argument>();


    public PlugListenConnector001_01() {

        super(plugListenConnectorName,
            plugListenConnectorDescription,
            plugListenConnectorTransport,
            plugListenConnectorDefaultArguments);
    }

} // end of PlugListenConnector001_01 class
