/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
import jdk.test.lib.dcmd.*;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test
 * @bug 8165736
 * @library /test/lib
 * @run testng AttachNoEntry
 */
public class AttachNoEntry extends AttachFailedTestBase {
    @Override
    public void run(CommandExecutor executor)  {
        try {
            String libpath = getSharedObjectPath("HasNoEntryPoint");
            OutputAnalyzer output = null;

            output = executor.execute("JVMTI.agent_load " + libpath);
            output.shouldContain("Agent_OnAttach");
            output.shouldContain("is not available");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
