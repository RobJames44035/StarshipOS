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

public class PlugTransportService004_01 extends PlugTransportService {

    static String plugTransportServiceName = "PlugTransportService004_01_Name";
    static String plugTransportServiceDescription = "PlugTransportService004_01_Description";
    static TransportService.Capabilities plugTransportServiceCapabilities =
        new TestCapabilities(
            true,  // supportsAcceptTimeout
            true,  // supportsAttachTimeout
            true,  // supportsHandshakeTimeout
            true   // supportsMultipleConnections
            );

    public PlugTransportService004_01() {

        super(
            plugTransportServiceName,
            plugTransportServiceDescription,
            plugTransportServiceCapabilities
            );
    }

} // end of PlugTransportService004_01 class
