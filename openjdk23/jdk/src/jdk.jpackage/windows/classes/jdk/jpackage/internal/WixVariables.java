/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jpackage.internal;

import java.util.HashMap;
import java.util.Map;

final class WixVariables {

    void defineWixVariable(String variableName) {
        setWixVariable(variableName, "yes");
    }

    void setWixVariable(String variableName, String variableValue) {
        values.put(variableName, variableValue);
    }

    Map<String, String> getValues() {
        return values;
    }

    private final Map<String, String> values = new HashMap<>();
}
