/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Category({"Java Development Kit", "Security"})
@Label("Initial Security Property")
@Name("jdk.InitialSecurityProperty")
@Description("Initial Security Properties")
public final class InitialSecurityPropertyEvent extends AbstractPeriodicEvent {
    @Label("Key")
    public String key;

    @Label("Value")
    public String value;
}
