/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple TransportService used by
 * nsk/jdi/PlugConnectors/TransportService/transportService001 test
 */

package nsk.jdi.PlugConnectors.TransportService.transportService001.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.connect.spi.*;
import java.util.*;

public class PlugTransportService001 extends PlugTransportService {

    static String plugTransportServiceName = "PlugTransportService001_Name";
    static String plugTransportServiceDescription = "PlugTransportService001_Description";
    static TransportService.Capabilities plugTransportServiceCapabilities =
        new TestCapabilities(
            true,  // supportsAcceptTimeout
            true,  // supportsAttachTimeout
            true,  // supportsHandshakeTimeout
            true   // supportsMultipleConnections
            );

    public PlugTransportService001() {

        super(
            plugTransportServiceName,
            plugTransportServiceDescription,
            plugTransportServiceCapabilities
            );
    }

} // end of PlugTransportService001 class
