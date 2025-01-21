/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6249843 6705893
 * @summary Test basic script compilation. Value eval'ed from
 * compiled and interpreted scripts should be same.
 */

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test6 {
        public static void main(String[] args) throws Exception {
            System.out.println("\nTest6\n");
            ScriptEngineManager m = new ScriptEngineManager();
            ScriptEngine engine = Helper.getJsEngine(m);
            if (engine == null) {
                System.out.println("Warning: No js engine found; test vacuously passes.");
                return;
            }

            try (Reader reader = new FileReader(
                new File(System.getProperty("test.src", "."), "Test6.js"))) {
                engine.eval(reader);
            }
            Object res = engine.get("res");

            CompiledScript scr = null;
            try (Reader reader = new FileReader(
                new File(System.getProperty("test.src", "."), "Test6.js"))) {
                scr = ((Compilable)engine).compile(reader);
            }

            if (scr == null) {
                throw new RuntimeException("compilation failed!");
            }

            scr.eval();
            Object res1 = engine.get("res");
            if (! res.equals(res1)) {
                throw new RuntimeException("values not equal");
            }
        }
}
