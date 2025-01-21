/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * A Simple LaunchingConnector with default arguments of all types used by
 * nsk/jdi/PlugConnectors/MultiConnectors/plugMultiConnect005 test
 */

package nsk.jdi.PlugConnectors.MultiConnectors.plugMultiConnect005.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import java.util.*;
import java.util.ArrayList;

public class PlugLaunchConnector005_02 extends PlugConnectors implements LaunchingConnector {

    static String plugLaunchConnectorName
        = "PlugLaunchConnector005_02_Name";
    static String plugLaunchConnectorDescription
        = "PlugLaunchConnector005_02_Description";
    static Transport plugLaunchConnectorTransport
        = new PlugConnectorsTransport("PlugLaunchConnector005_02_Transport");
    static Map<String, Connector.Argument> plugLaunchConnectorDefaultArguments
        = new HashMap<String, Connector.Argument>();

    static Map<String, Connector.Argument> prepareConnectorDefaultArguments() {
        String plugLaunchConnectorStringArgumentKey = "PlugLaunchConnector005_02_StringArgument_Key";
        Connector.StringArgument testStringArgument = new TestStringArgument(
            "PlugLaunchConnector005_02_StringArgument_Name",
            "PlugLaunchConnector005_02_StringArgument_Label",
            "PlugLaunchConnector005_02_StringArgument_Description",
            "PlugLaunchConnector005_02_StringArgument_Value",
            true  // mustSpecify
            );
        plugLaunchConnectorDefaultArguments.put(plugLaunchConnectorStringArgumentKey, testStringArgument);

        String plugLaunchConnectorIntegerArgumentKey = "PlugLaunchConnector005_02_IntegerArgument_Key";
        Connector.IntegerArgument testIntegerArgument = new TestIntegerArgument(
            "PlugLaunchConnector005_02_IntegerArgument_Name",
            "PlugLaunchConnector005_02_IntegerArgument_Label",
            "PlugLaunchConnector005_02_IntegerArgument_Description",
            555555, // IntegerArgument_Value",
            111111, // IntegerArgument_Min",
            999999, // IntegerArgument_Max",
            true    // mustSpecify
            );
        plugLaunchConnectorDefaultArguments.put(plugLaunchConnectorIntegerArgumentKey, testIntegerArgument);

        String plugLaunchConnectorBooleanArgumentKey = "PlugLaunchConnector005_02_BooleanArgument_Key";
        Connector.BooleanArgument testBooleanArgument = new TestBooleanArgument(
            "PlugLaunchConnector005_02_BooleanArgument_Name",
            "PlugLaunchConnector005_02_BooleanArgument_Label",
            "PlugLaunchConnector005_02_BooleanArgument_Description",
            true, // BooleanArgument_Value",
            true    // mustSpecify
            );
        plugLaunchConnectorDefaultArguments.put(plugLaunchConnectorBooleanArgumentKey, testBooleanArgument);

        String plugLaunchConnectorSelectedArgumentKey = "PlugLaunchConnector005_02_SelectedArgument_Key";
        List<String> selectedArgumentChoices = new ArrayList<String>();
        selectedArgumentChoices.add("PlugLaunchConnector005_02_SelectedArgument_Value_0");
        selectedArgumentChoices.add("PlugLaunchConnector005_02_SelectedArgument_Value");
        selectedArgumentChoices.add("PlugLaunchConnector005_02_SelectedArgument_Value_1");

        Connector.SelectedArgument testSelectedArgument = new TestSelectedArgument(
            "PlugLaunchConnector005_02_SelectedArgument_Name",
            "PlugLaunchConnector005_02_SelectedArgument_Label",
            "PlugLaunchConnector005_02_SelectedArgument_Description",
            "PlugLaunchConnector005_02_SelectedArgument_Value",
            selectedArgumentChoices, // List of choices,
            true    // mustSpecify
            );
        plugLaunchConnectorDefaultArguments.put(plugLaunchConnectorSelectedArgumentKey, testSelectedArgument);

        return plugLaunchConnectorDefaultArguments;
    }  // end of prepareConnectorDefaultArguments() method


    public PlugLaunchConnector005_02() {

        super(plugLaunchConnectorName,
            plugLaunchConnectorDescription,
            plugLaunchConnectorTransport,
            prepareConnectorDefaultArguments());
    }

} // end of PlugLaunchConnector005_02 class
