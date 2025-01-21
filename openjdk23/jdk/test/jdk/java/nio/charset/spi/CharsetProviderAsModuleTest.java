/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8340404
 * @summary Check that a CharsetProvider SPI can be deployed as a module
 * @build provider/*
 * @run main/othervm CharsetProviderAsModuleTest
 */

import java.nio.charset.Charset;

public class CharsetProviderAsModuleTest {

    // Basic test ensures that our BAZ charset is loaded via the BazProvider
    public static void main(String[] args) {
        var cs = Charset.availableCharsets();
        Charset bazCs;
        // check provider is providing BAZ via charsets()
        if (!cs.containsKey("BAZ")) {
            throw new RuntimeException("SPI BazProvider did not provide BAZ Charset");
        } else {
            bazCs = cs.get("BAZ");
            // check provider is in a named module
            if (!bazCs.getClass().getModule().isNamed()) {
                throw new RuntimeException("BazProvider is not a named module");
            }
            var aliases = bazCs.aliases();
            // check BAZ cs aliases were loaded correctly
            if (!aliases.contains("BAZ-1") || !aliases.contains("BAZ-2")) {
                throw new RuntimeException("BAZ Charset did not provide correct aliases");
            }
            // check provider implements charsetForName()
            if (!bazCs.equals(Charset.forName("BAZ"))) {
                throw new RuntimeException("SPI BazProvider provides bad charsetForName()");
            }
        }
    }
}
