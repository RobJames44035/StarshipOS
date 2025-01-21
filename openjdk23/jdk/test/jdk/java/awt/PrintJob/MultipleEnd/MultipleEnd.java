/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/**
 * @test
 * @key headful printer
 * @bug 4112758
 * @summary Checks that a second invocation of PrintJob.end() does not cause
 * an exception or segmentation violation.
 * @author dpm
 */

import java.awt.*;

public class MultipleEnd {
    public static void main(String[] args) {
        new MultipleEnd().start();
    }
    public void start() {
        new MultipleEndFrame();
    }
}

class MultipleEndFrame extends Frame {
    public MultipleEndFrame() {
        super("MultipleEnd");
        setVisible(true);

        JobAttributes job = new JobAttributes();
        job.setDialog(JobAttributes.DialogType.NONE);
        PrintJob pj  = getToolkit().getPrintJob(this, "MultipleEnd", job, null);
        if (pj != null) {
            pj.end();
            pj.end();
        }
    }
}
