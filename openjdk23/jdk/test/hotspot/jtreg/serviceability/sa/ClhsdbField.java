/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.test.lib.apps.LingeredApp;
import jtreg.SkippedException;

/**
 * @test
 * @bug 8191538
 * @summary Test clhsdb 'field' command
 * @requires vm.hasSA
 * @library /test/lib
 * @run main/othervm ClhsdbField
 */

public class ClhsdbField {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting ClhsdbField test");

        LingeredApp theApp = null;
        try {
            ClhsdbLauncher test = new ClhsdbLauncher();
            theApp = LingeredApp.startApp();
            System.out.println("Started LingeredApp with pid " + theApp.getPid());

            List<String> cmds = List.of("field");

            Map<String, List<String>> expStrMap = new HashMap<>();
            expStrMap.put("field", List.of(
                "field ConstantPool _pool_holder InstanceKlass*",
                "field InstanceKlass _methods Array<Method*>*",
                "field InstanceKlass _constants ConstantPool*",
                "field Klass _name Symbol*",
                "field Thread _osthread OSThread*",
                "field TenuredGeneration _the_space ContiguousSpace*",
                "field VirtualSpace _low_boundary char*",
                "field MethodCounters _backedge_counter InvocationCounter",
                "field nmethod _entry_bci int",
                "field Universe _collectedHeap CollectedHeap"));
            test.run(theApp.getPid(), cmds, expStrMap, null);
        } catch (SkippedException se) {
            throw se;
        } catch (Exception ex) {
            throw new RuntimeException("Test ERROR " + ex, ex);
        } finally {
            LingeredApp.stopApp(theApp);
        }
        System.out.println("Test PASSED");
    }
}
