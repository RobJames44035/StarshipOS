/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package test.jfr.event;

import test.jfr.annotation.ModularizedAnnotation;

import jdk.jfr.Event;
import jdk.jfr.SettingDefinition;
import test.jfr.setting.ModularizedSetting;

@ModularizedAnnotation("hello type")
public class ModularizedPeriodicEvent extends Event {

    @ModularizedAnnotation("hello field")
    public String message;

    @SettingDefinition
    boolean filter(ModularizedSetting ms) {
        return ms.accept();
    }
}
