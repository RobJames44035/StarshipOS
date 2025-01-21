/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */
package com.sun.hotspot.igv.filter;

import com.sun.hotspot.igv.graph.Diagram;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import org.openide.cookies.OpenCookie;
import org.openide.util.Exceptions;

/**
 *
 * @author Thomas Wuerthinger
 */
public class CustomFilter extends AbstractFilter {

    private String code;
    private String name;
    private final ScriptEngine engine;

    public CustomFilter(String name, String code, ScriptEngine engine) {
        this.name = name;
        this.code = code;
        this.engine = engine;
        getProperties().setProperty("name", name);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setName(String s) {
        name = s;
    }

    public void setCode(String s) {
        code = s;
    }

    @Override
    public OpenCookie getEditor() {
        return this::openInEditor;
    }

    public boolean openInEditor() {
        EditFilterDialog dialog = new EditFilterDialog(CustomFilter.this);
        dialog.setVisible(true);
        boolean accepted = dialog.wasAccepted();
        if (accepted) {
            getChangedEvent().fire();
        }
        return accepted;
    }

    @Override
    public String toString() {
        return getName();
    }


    @Override
    public void apply(Diagram d) {
        try {
            Bindings b = engine.getContext().getBindings(ScriptContext.ENGINE_SCOPE);
            b.put("graph", d);
            engine.eval(code, b);
        } catch (ScriptException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
