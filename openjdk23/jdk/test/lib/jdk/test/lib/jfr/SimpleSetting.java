/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.test.lib.jfr;

import java.util.Set;

import jdk.jfr.SettingControl;

public class SimpleSetting extends SettingControl {

    @Override
    public String combine(Set<String> settingValue) {
        return "none";
    }

    @Override
    public void setValue(String value) {
    }

    @Override
    public String getValue() {
        return "none";
    }
}
