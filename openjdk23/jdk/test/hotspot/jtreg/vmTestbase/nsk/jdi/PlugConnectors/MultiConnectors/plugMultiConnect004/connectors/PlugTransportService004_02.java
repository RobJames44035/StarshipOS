/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple TransportService used by
 * nsk/jdi/PlugConnectors/MultiConnectors/plugMultiConnect004 test
 */

package nsk.jdi.PlugConnectors.MultiConnectors.plugMultiConnect004.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.connect.spi.*;
import java.util.*;

public class PlugTransportService004_02 extends PlugTransportService {

    static String plugTransportServiceName = "PlugTransportService004_02_Name";
    static String plugTransportServiceDescription = "PlugTransportService004_02_Description";
    static TransportService.Capabilities plugTransportServiceCapabilities =
        new TestCapabilities(
            false,  // supportsAcceptTimeout
            false,  // supportsAttachTimeout
            false,  // supportsHandshakeTimeout
            false   // supportsMultipleConnections
            );

    public PlugTransportService004_02() {

        super(
            plugTransportServiceName,
            plugTransportServiceDescription,
            plugTransportServiceCapabilities
            );
    }

} // end of PlugTransportService004_02 class
