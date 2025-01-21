/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 *
 */

package bench;

import java.io.IOException;
import java.util.Properties;

/**
 * Objects implementing this interface are used for printing benchmark reports.
 */
public interface Reporter {
    /**
     * Write benchmark report to the given stream.
     */
    void writeReport(BenchInfo[] binfo, Properties props)
        throws IOException;
}
