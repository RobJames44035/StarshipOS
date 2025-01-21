/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6346729 6705893
 * @summary Create JavaScript engine and check language and engine version
 * @modules java.scripting
 */

import javax.script.*;
import java.io.*;

public class VersionTest  {
        private static final String JS_LANG_VERSION = "ECMA - 262 Edition 5.1";

        public static void main(String[] args) throws Exception {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine jsengine = Helper.getJsEngine(manager);
            if (jsengine == null) {
                System.out.println("Warning: No js engine found; test vacuously passes.");
                return;
            }
            String langVersion = jsengine.getFactory().getLanguageVersion();
            if (! langVersion.equals(JS_LANG_VERSION)) {
                throw new RuntimeException("Expected JavaScript version is " +
                            JS_LANG_VERSION);
            }
            String engineVersion = jsengine.getFactory().getEngineVersion();
            String expectedVersion = getNashornVersion();
            if (! engineVersion.equals(expectedVersion)) {
                throw new RuntimeException("Expected version is " + expectedVersion);
            }
        }

        private static String getNashornVersion() {
            try {
                Class versionClass = Class.forName("jdk.nashorn.internal.runtime.Version");
                return (String) versionClass.getMethod("version").invoke(null);
            } catch (Exception e) {
                return "Version Unknown!";
            }
        }
}
