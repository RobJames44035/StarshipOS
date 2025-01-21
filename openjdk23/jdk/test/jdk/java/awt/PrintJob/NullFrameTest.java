/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
/*
 * @test
 * @bug 8154057
 * @summary  getPrintJob doesn't throw NPE if frame is null
 * @run main NullFrameTest
 */
import java.awt.JobAttributes;
import java.awt.Toolkit;

public class NullFrameTest {
    public static void main(String[] args) {
        JobAttributes ja = new JobAttributes();
        ja.setDialog(JobAttributes.DialogType.COMMON);
        boolean npeThrown = false;
        try {
            Toolkit.getDefaultToolkit().getPrintJob(null,
                                               "test Printing", ja, null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw
            new RuntimeException("getPrintJob didn't throw NPE for null Frame");
        }
    }
}
