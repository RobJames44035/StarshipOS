/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package javax.print;

/**
 * Interface {@code MultiPrintService} is the factory for a
 * {@code MultiDocPrintJob}. A {@code MultiPrintService} describes the
 * capabilities of a printer and can be queried regarding a printer's supported
 * attributes.
 */
public interface MultiDocPrintService extends PrintService {

    /**
     * Create a job which can print a multiDoc.
     *
     * @return a {@code MultiDocPrintJob}
     */
    public MultiDocPrintJob createMultiDocPrintJob();
}
