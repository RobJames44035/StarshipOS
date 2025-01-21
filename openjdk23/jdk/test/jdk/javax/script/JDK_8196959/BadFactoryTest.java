/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import org.junit.jupiter.api.Test;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * @test
 * @bug 8196959 8320712
 * @summary Verify that ScriptEngineManager can load BadFactory without throwing NPE
 * @build BadFactory BadFactoryTest
 * @run junit/othervm BadFactoryTest
 */
public class BadFactoryTest {

    @Test
    public void scriptEngineManagerShouldLoadBadFactory() {
        // Check that ScriptEngineManager initializes even in the
        // presence of a ScriptEngineFactory returning nulls
        ScriptEngineManager m = new ScriptEngineManager();

        // Sanity check that ScriptEngineManager actually found the BadFactory
        Optional<ScriptEngineFactory> badFactory = m.getEngineFactories().stream()
                .filter(fac -> fac.getClass() == BadFactory.class)
                .findAny();
        assertTrue(badFactory.isPresent(), "BadFactory not found");
    }
}
