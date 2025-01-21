/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple ListeningConnector used by
 * nsk/jdi/PlugConnectors/ListenConnector/plugListenConnect003 test
 */

package nsk.jdi.PlugConnectors.ListenConnector.plugListenConnect003.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import java.util.*;
import java.util.ArrayList;

public class PlugListenConnector003 extends PlugConnectors implements ListeningConnector {

    static String plugListenConnectorName = "PlugListenConnector003_Name";
    static String plugListenConnectorDescription = "PlugListenConnector003_Description";
    static Transport plugListenConnectorTransport = new PlugConnectorsTransport("PlugListenConnector003_Transport");
    static Map<String, Connector.Argument>  plugListenConnectorDefaultArguments = new HashMap<String, Connector.Argument>();

    static Map<String, Connector.Argument>  prepareConnectorDefaultArguments() {
        String plugListenConnectorStringArgumentKey = "PlugListenConnector003_StringArgument_Key";
        Connector.StringArgument testStringArgument = new TestStringArgument(
            "PlugListenConnector003_StringArgument_Name",
            "PlugListenConnector003_StringArgument_Label",
            "PlugListenConnector003_StringArgument_Description",
            "PlugListenConnector003_StringArgument_Value",
            true  // mustSpecify
            );
        plugListenConnectorDefaultArguments.put(plugListenConnectorStringArgumentKey, testStringArgument);

        String plugListenConnectorIntegerArgumentKey = "PlugListenConnector003_IntegerArgument_Key";
        Connector.IntegerArgument testIntegerArgument = new TestIntegerArgument(
            "PlugListenConnector003_IntegerArgument_Name",
            "PlugListenConnector003_IntegerArgument_Label",
            "PlugListenConnector003_IntegerArgument_Description",
            555555, // IntegerArgument_Value",
            111111, // IntegerArgument_Min",
            999999, // IntegerArgument_Max",
            true    // mustSpecify
            );
        plugListenConnectorDefaultArguments.put(plugListenConnectorIntegerArgumentKey, testIntegerArgument);

        String plugListenConnectorBooleanArgumentKey = "PlugListenConnector003_BooleanArgument_Key";
        Connector.BooleanArgument testBooleanArgument = new TestBooleanArgument(
            "PlugListenConnector003_BooleanArgument_Name",
            "PlugListenConnector003_BooleanArgument_Label",
            "PlugListenConnector003_BooleanArgument_Description",
            true, // BooleanArgument_Value",
            true    // mustSpecify
            );
        plugListenConnectorDefaultArguments.put(plugListenConnectorBooleanArgumentKey, testBooleanArgument);

        String plugListenConnectorSelectedArgumentKey = "PlugListenConnector003_SelectedArgument_Key";
        List<String> selectedArgumentChoices = new ArrayList<String>();
        selectedArgumentChoices.add("PlugListenConnector003_SelectedArgument_Value_0");
        selectedArgumentChoices.add("PlugListenConnector003_SelectedArgument_Value");
        selectedArgumentChoices.add("PlugListenConnector003_SelectedArgument_Value_1");

        Connector.SelectedArgument testSelectedArgument = new TestSelectedArgument(
            "PlugListenConnector003_SelectedArgument_Name",
            "PlugListenConnector003_SelectedArgument_Label",
            "PlugListenConnector003_SelectedArgument_Description",
            "PlugListenConnector003_SelectedArgument_Value",
            selectedArgumentChoices, // List of choices,
            true    // mustSpecify
            );
        plugListenConnectorDefaultArguments.put(plugListenConnectorSelectedArgumentKey, testSelectedArgument);

        return plugListenConnectorDefaultArguments;
    }  // end of prepareConnectorDefaultArguments() method


    public PlugListenConnector003() {

        super(plugListenConnectorName,
            plugListenConnectorDescription,
            plugListenConnectorTransport,
            prepareConnectorDefaultArguments());

        String exceptionMessage =
            "<## PlugListenConnector003: This RuntimeException is thrown intentionally by ListeningConnector "
            + "constructor to check creating of pluggable connectors on base of such ListeningConnector. ##>";

        throw new RuntimeException(exceptionMessage);
    }

} // end of PlugListenConnector003 class
