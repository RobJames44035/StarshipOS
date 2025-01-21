/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple TransportService used by
 * nsk/jdi/PlugConnectors/TransportService/transportService002 test
 */

package nsk.jdi.PlugConnectors.TransportService.transportService002.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.connect.spi.*;
import java.util.*;

public class PlugTransportService002 extends PlugTransportService {

    static String plugTransportServiceName = "PlugTransportService002_Name";
    static String plugTransportServiceDescription = "PlugTransportService002_Description";
    static TransportService.Capabilities plugTransportServiceCapabilities =
        new TestCapabilities(
            false,  // supportsAcceptTimeout
            false,  // supportsAttachTimeout
            false,  // supportsHandshakeTimeout
            false   // supportsMultipleConnections
            );

    public PlugTransportService002() {

        super(
            plugTransportServiceName,
            plugTransportServiceDescription,
            plugTransportServiceCapabilities
            );
    }

} // end of PlugTransportService002 class
