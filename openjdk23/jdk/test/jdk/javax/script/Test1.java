/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6249843 6705893
 * @summary Create JavaScript engine and execute a simple script.
 * Tests script engine discovery mechanism.
 */

import java.io.File;
import java.io.FileReader;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test1  {
        public static void main(String[] args) throws Exception {
            System.out.println("\nTest1\n");
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine jsengine = Helper.getJsEngine(manager);
            if (jsengine == null) {
                     System.out.println("Warning: No js engine found; test vacuously passes.");
                     return;
            }
            jsengine.eval(new FileReader(
                     new File(System.getProperty("test.src", "."), "Test1.js")));
        }
}
