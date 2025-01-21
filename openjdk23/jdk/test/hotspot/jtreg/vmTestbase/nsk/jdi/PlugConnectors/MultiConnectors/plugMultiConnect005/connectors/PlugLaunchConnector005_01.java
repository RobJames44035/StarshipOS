/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple LaunchingConnector without default arguments used by
 * nsk/jdi/PlugConnectors/MultiConnectors/plugMultiConnect005 test
 */

package nsk.jdi.PlugConnectors.MultiConnectors.plugMultiConnect005.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import java.util.*;

public class PlugLaunchConnector005_01 extends PlugConnectors implements LaunchingConnector {

    static String plugLaunchConnectorName
        = "PlugLaunchConnector005_01_Name";
    static String plugLaunchConnectorDescription
        = "PlugLaunchConnector005_01_Description";
    static Transport plugLaunchConnectorTransport
        = new PlugConnectorsTransport("PlugLaunchConnector005_01_Transport");
    static Map<java.lang.String,com.sun.jdi.connect.Connector.Argument> plugLaunchConnectorDefaultArguments
        = new HashMap<java.lang.String,com.sun.jdi.connect.Connector.Argument>();


    public PlugLaunchConnector005_01() {

        super(plugLaunchConnectorName,
            plugLaunchConnectorDescription,
            plugLaunchConnectorTransport,
            plugLaunchConnectorDefaultArguments);
    }

} // end of PlugLaunchConnector005_01 class
