/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 *
 *
 * This is script engine factory for dummyRhino engine.
 */
package jdk.dummyRhino.api.scripting;
import javax.script.*;
import java.util.*;

public class DummyRhinoJSEngineFactory implements ScriptEngineFactory {
    public String getEngineName() {
        return "dummyRhino";
    }

    public String getEngineVersion() {
        return "-1.0";
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public String getLanguageName() {
        return "dummyRhino";
    }

    public String getLanguageVersion() {
        return "-1.0";
    }

    public String getMethodCallSyntax(String obj, String m, String... args) {
        StringBuffer buf = new StringBuffer();
        buf.append("call " + m + " ");
        buf.append(" on " + obj + " with ");
        for (int i = 0; i < args.length; i++) {
            buf.append(args[i] + ", ");
        }
        buf.append(";");
        return buf.toString();
    }

    public List<String> getMimeTypes() {
        return mimeTypes;
    }

    public List<String> getNames() {
        return names;
    }

    public String getOutputStatement(String str) {
        return "output " + str;
    }

    public String getParameter(String key) {
        if (key.equals(ScriptEngine.ENGINE)) {
            return getEngineName();
        } else if (key.equals(ScriptEngine.ENGINE_VERSION)) {
            return getEngineVersion();
        } else if (key.equals(ScriptEngine.NAME)) {
            return getEngineName();
        } else if (key.equals(ScriptEngine.LANGUAGE)) {
            return getLanguageName();
        } else if (key.equals(ScriptEngine.LANGUAGE_VERSION)) {
            return getLanguageVersion();
        } else {
            return null;
        }
    }

    public String getProgram(String... statements) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < statements.length; i++) {
            buf.append(statements[i]);
        }
        return buf.toString();
    }

    public ScriptEngine getScriptEngine() {
        return new DummyRhinoJSEngine();
    }

    private static List<String> names;
    private static List<String> extensions;
    private static List<String> mimeTypes;
    static {
        names = new ArrayList<String>(1);
        names.add("dummyRhino");
        names.add("js");
        names = Collections.unmodifiableList(names);
        extensions = names;
        mimeTypes = new ArrayList<String>(0);
        mimeTypes = Collections.unmodifiableList(mimeTypes);
    }
}
