/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Checks that jstack correctly prints the thread names
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library ../share
 * @run main/othervm -XX:+UsePerfData ThreadNamesTest
 */
import common.ToolResults;
import utils.*;

public class ThreadNamesTest {

    private static final String STRANGE_NAME = "-_?+!@#$%^*()";
    private static final String LONG_NAME = "loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong";

    static class NamedThread extends Thread {

        NamedThread(String name) {
            setName(name);
        }

        @Override
        public void run() {
            Utils.sleep();
        }
    }

    public static void main(String[] args) throws Exception {
        testWithName(STRANGE_NAME);
        testWithName("");
        testWithName(LONG_NAME);
    }

    private static void testWithName(String name) throws Exception {
        // Start a thread with some strange name
        NamedThread thread = new NamedThread(name);
        thread.start();

        // Run jstack tool and collect the output
        JstackTool jstackTool = new JstackTool(ProcessHandle.current().pid());
        ToolResults results = jstackTool.measure();

        // Analyze the jstack output for the strange thread name
        JStack jstack1 = new DefaultFormat().parse(results.getStdoutString());
        ThreadStack ti1 = jstack1.getThreadStack(name);

        if (ti1 == null) {
            throw new RuntimeException("jstack output doesn't contain thread info for the thread '" + name + "'");
        }
    }

}
