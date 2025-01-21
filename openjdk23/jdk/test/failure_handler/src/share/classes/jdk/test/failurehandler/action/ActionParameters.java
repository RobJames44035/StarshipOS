/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.failurehandler.action;

import jdk.test.failurehandler.value.DefaultValue;
import jdk.test.failurehandler.value.Value;

public class ActionParameters {
    @Value (name = "repeat")
    @DefaultValue (value = "1")
    public int repeat = 1;

    @Value (name = "pause")
    @DefaultValue (value = "500")
    public long pause = 500;

    @Value (name = "stopOnError")
    @DefaultValue (value = "false")
    public boolean stopOnError = false;

    @Value (name = "timeout")
    @DefaultValue (value = "" + 20_000L)
    public long timeout = -1L;

    @Value (name = "successArtifacts")
    @DefaultValue (value = "")
    public String successArtifacts = "";

    public ActionParameters() { }
}
