/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8072853
 * @summary SimpleScriptContext used by NashornScriptEngine doesn't completely complies to the spec regarding exception throwing
 * @run testng SimpleScriptContextNameChecksTest
 */

import java.util.List;
import java.util.function.Consumer;
import javax.script.*;
import org.testng.annotations.Test;

public class SimpleScriptContextNameChecksTest {
    private List<ScriptEngineFactory> getFactories() {
        return new ScriptEngineManager().getEngineFactories();
    }

    private void testAndExpect(Consumer<ScriptContext> c, Class<? extends RuntimeException> clazz) {
        for (ScriptEngineFactory fac : getFactories()) {
            ScriptContext sc = fac.getScriptEngine().getContext();
            String name = fac.getEngineName();
            try {
                c.accept(sc);
                throw new RuntimeException("no exception for " + name);
            } catch (NullPointerException | IllegalArgumentException e) {
                if (e.getClass() == clazz) {
                    System.out.println("got " + e + " as expected for " + name);
                } else {
                    throw e;
                }
            }
        }
    }

    private void testAndExpectIAE(Consumer<ScriptContext> c) {
        testAndExpect(c, IllegalArgumentException.class);
    }

    private void testAndExpectNPE(Consumer<ScriptContext> c) {
        testAndExpect(c, NullPointerException.class);
    }

    @Test
    public void getAttributeEmptyName() {
        testAndExpectIAE(sc -> sc.getAttribute("", ScriptContext.GLOBAL_SCOPE));
    }

    @Test
    public void getAttributeNullName() {
        testAndExpectNPE(sc -> sc.getAttribute(null, ScriptContext.GLOBAL_SCOPE));
    }

    @Test
    public void removeAttributeEmptyName() {
        testAndExpectIAE(sc -> sc.removeAttribute("", ScriptContext.GLOBAL_SCOPE));
    }

    @Test
    public void removeAttributeNullName() {
        testAndExpectNPE(sc -> sc.removeAttribute(null, ScriptContext.GLOBAL_SCOPE));
    }

    @Test
    public void setAttributeEmptyName() {
        testAndExpectIAE(sc -> sc.setAttribute("", "value", ScriptContext.GLOBAL_SCOPE));
    }

    @Test
    public void setAttributeNullName() {
        testAndExpectNPE(sc -> sc.setAttribute(null, "value", ScriptContext.GLOBAL_SCOPE));
    }

    @Test
    public void getAttributesScopeEmptyName() {
        testAndExpectIAE(sc -> sc.getAttributesScope(""));
    }

    @Test
    public void getAttributesScopeNullName() {
        testAndExpectNPE(sc -> sc.getAttributesScope(null));
    }
}
