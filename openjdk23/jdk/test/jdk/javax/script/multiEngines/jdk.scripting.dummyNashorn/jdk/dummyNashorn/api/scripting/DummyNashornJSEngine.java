/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 *
 *
 * This is a dummyNashorn script engine implementation
 */
package jdk.dummyNashorn.api.scripting;
import javax.script.*;
import java.io.*;

public class DummyNashornJSEngine extends AbstractScriptEngine {
    public Object eval(String str, ScriptContext ctx) {
        return eval(new StringReader(str), ctx);
    }

    public Object eval(Reader reader, ScriptContext ctx) {
        System.out.println("eval done!");
        return null;
    }

    public ScriptEngineFactory getFactory() {
        return new DummyNashornJSEngineFactory();
    }

    public Bindings createBindings() {
        return new SimpleBindings();
    }
}
