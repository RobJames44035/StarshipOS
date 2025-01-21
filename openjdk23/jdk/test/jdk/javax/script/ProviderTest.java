/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 *
 *
 * Unit test for testing script engine discovery.
 */
import javax.script.*;

public class ProviderTest {
    public static void main(String args[]) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine se = manager.getEngineByName("dummy");
        if (se == null) {
            throw new RuntimeException("can't locate dummy engine");
        }
        se = Helper.getJsEngine(manager);
        if (se == null) {
            System.out.println("Warning: No js engine found; test vacuously passes.");
            return;
        }
    }
}
