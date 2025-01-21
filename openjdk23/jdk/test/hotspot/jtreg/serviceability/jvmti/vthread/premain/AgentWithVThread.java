/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
import java.lang.instrument.Instrumentation;

public class AgentWithVThread {

    static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation instrumentation) {
        var thread = Thread.startVirtualThread(() -> {
            System.out.println("passed");
        });
        try {
            thread.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
