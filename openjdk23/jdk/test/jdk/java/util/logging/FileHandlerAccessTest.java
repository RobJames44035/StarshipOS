/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @test
 * @bug 8252883
 * @summary tests the handling of AccessDeniedException due to delay in Windows deletion.
 * @modules java.logging/java.util.logging:open
 * @run main/othervm FileHandlerAccessTest 20
 * @author evwhelan
 */

public class FileHandlerAccessTest {
    public static void main(String[] args) {
        var count = Integer.parseInt(args[0]);
        System.out.println("Testing with " + count + " threads");
        for (var i = 0; i < count; i++) {
            new Thread(FileHandlerAccessTest::access).start();
        }
    }

    private static void access() {
        try {
            var handler = new FileHandler("sample%g.log", 1048576, 2, true);
            handler.publish(new LogRecord(Level.SEVERE, "TEST"));
            handler.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
