/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple TransportService used by
 * nsk/jdi/PlugConnectors/MultiConnectors/plugMultiConnect006 test
 */

package nsk.jdi.PlugConnectors.MultiConnectors.plugMultiConnect006.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.connect.spi.*;
import java.util.*;

public class PlugTransportService006_02 extends PlugTransportService {

    static String plugTransportServiceName = "PlugTransportService006_02_Name";
    static String plugTransportServiceDescription = "PlugTransportService006_02_Description";
    static TransportService.Capabilities plugTransportServiceCapabilities =
        new TestCapabilities(
            false,  // supportsAcceptTimeout
            false,  // supportsAttachTimeout
            false,  // supportsHandshakeTimeout
            false   // supportsMultipleConnections
            );

    public PlugTransportService006_02() {

        super(
            plugTransportServiceName,
            plugTransportServiceDescription,
            plugTransportServiceCapabilities
            );
    }

} // end of PlugTransportService006_02 class
