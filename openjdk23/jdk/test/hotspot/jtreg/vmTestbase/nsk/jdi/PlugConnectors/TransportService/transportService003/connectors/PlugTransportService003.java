/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple TransportService used by
 * nsk/jdi/PlugConnectors/TransportService/transportService003 test
 */

package nsk.jdi.PlugConnectors.TransportService.transportService003.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.connect.spi.*;
import java.util.*;

public class PlugTransportService003 extends PlugTransportService {

    static String plugTransportServiceName = "PlugTransportService003_Name";
    static String plugTransportServiceDescription = "PlugTransportService003_Description";
    static TransportService.Capabilities plugTransportServiceCapabilities =
        new TestCapabilities(
            true,  // supportsAcceptTimeout
            true,  // supportsAttachTimeout
            true,  // supportsHandshakeTimeout
            true   // supportsMultipleConnections
            );

    public PlugTransportService003() {

        super(
            plugTransportServiceName,
            plugTransportServiceDescription,
            plugTransportServiceCapabilities
            );

        String exceptionMessage =
            "<## PlugTransportService003: This RuntimeException is thrown intentionally by TransportService "
            + "constructor to check creating of pluggable connectors on base of such TransportService. ##>";

        throw new RuntimeException(exceptionMessage);

    }

} // end of PlugTransportService003 class
