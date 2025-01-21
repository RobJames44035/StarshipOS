/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.failurehandler.action;

import jdk.test.failurehandler.HtmlSection;
import jdk.test.failurehandler.value.InvalidValueException;
import jdk.test.failurehandler.value.SubValues;
import jdk.test.failurehandler.value.Value;
import jdk.test.failurehandler.value.ValueHandler;
import jdk.test.failurehandler.value.DefaultValue;

import java.io.PrintWriter;
import java.util.Properties;

public class SimpleAction implements Action {
    /* package-private */ final String[] sections;
    @Value(name = "javaOnly")
    @DefaultValue(value = "false")
    private boolean javaOnly = false;

    @Value (name = "app")
    private String app = null;

    @Value (name = "args")
    @DefaultValue (value = "")
    /* package-private */ String[] args = new String[]{};

    @SubValues(prefix = "params")
    private final ActionParameters params;

    public SimpleAction(String id, Properties properties)
            throws InvalidValueException {
        this(id, id, properties);
    }
    public SimpleAction(String name, String id, Properties properties)
            throws InvalidValueException {
        sections = name.split("\\.");
        this.params = new ActionParameters();
        ValueHandler.apply(this, properties, id);
    }

    public ProcessBuilder prepareProcess(PrintWriter log, ActionHelper helper) {
        ProcessBuilder process = helper.prepareProcess(log, app, args);
        if (process != null) {
            process.redirectErrorStream(true);
        }

        return process;
    }

    @Override
    public boolean isJavaOnly() {
        return javaOnly;
    }

    @Override
    public HtmlSection getSection(HtmlSection section) {
        return section.createChildren(sections);
    }

    @Override
    public ActionParameters getParameters() {
        return params;
    }
}
