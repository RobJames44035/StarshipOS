/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6249843 6705893
 * @summary Test engine and global scopes
 */

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

public class Test3 {
        public static void main(String[] args) throws Exception {
            System.out.println("\nTest3\n");
            final Reader reader = new FileReader(
                new File(System.getProperty("test.src", "."), "Test3.js"));
            ScriptEngineManager m = new ScriptEngineManager();
            final ScriptEngine engine = Helper.getJsEngine(m);
            if (engine == null) {
                System.out.println("Warning: No js engine found; test vacuously passes.");
                return;
            }
            Bindings en = new SimpleBindings();
            engine.setBindings(en, ScriptContext.ENGINE_SCOPE);
            en.put("key", "engine value");
            Bindings gn = new SimpleBindings();
            engine.setBindings(gn, ScriptContext.GLOBAL_SCOPE);
            gn.put("key", "global value");
            engine.eval(reader);
        }
}
