/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6249843 6705893
 * @summary Test invoking script function or method from Java
 */

import java.io.File;
import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test8 {
        public static void main(String[] args) throws Exception {
            System.out.println("\nTest8\n");
            ScriptEngineManager m = new ScriptEngineManager();
            ScriptEngine e  = Helper.getJsEngine(m);
            if (e == null) {
                System.out.println("Warning: No js engine found; test vacuously passes.");
                return;
            }
            e.eval(new FileReader(
                new File(System.getProperty("test.src", "."), "Test8.js")));
            Invocable inv = (Invocable)e;
            inv.invokeFunction("main", "Mustang");
            // use method of a specific script object
            Object scriptObj = e.get("scriptObj");
            inv.invokeMethod(scriptObj, "main", "Mustang");
        }
}
