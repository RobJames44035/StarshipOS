/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package org.pear;

import java.util.Arrays;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class PearScriptEngineFactory implements ScriptEngineFactory {

    public PearScriptEngineFactory() { }

    public static PearScriptEngineFactory provider() {
        throw new RuntimeException("Should not be called");
    }

    @Override
    public String getEngineName() {
        return "PearScriptEngine";
    }

    @Override
    public String getEngineVersion() {
        return "1.0";
    }

    @Override
    public List<String> getExtensions() {
        return Arrays.asList("pear");
    }

    @Override
    public List<String> getMimeTypes() {
        return Arrays.asList("application/x-pearscript");
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList("PearScript");
    }

    @Override
    public String getLanguageName() {
        return "PearScript";
    }

    @Override
    public String getLanguageVersion() {
        return "1.0";
    }

    @Override
    public Object getParameter(String key) {
        return null;
    }

    @Override
    public String getMethodCallSyntax(String obj, String m, String... args) {
        throw new RuntimeException();
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        throw new RuntimeException();
    }

    @Override
    public String getProgram(String... statements) {
        throw new RuntimeException();
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new PearScript();
    }
}
