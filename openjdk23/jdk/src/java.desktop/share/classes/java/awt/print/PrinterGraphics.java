/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package java.awt.print;

/**
 * The {@code PrinterGraphics} interface is implemented by
 * {@link java.awt.Graphics} objects that are passed to
 * {@link Printable} objects to render a page. It allows an
 * application to find the {@link PrinterJob} object that is
 * controlling the printing.
 */

public interface PrinterGraphics {

    /**
     * Returns the {@code PrinterJob} that is controlling the
     * current rendering request.
     * @return the {@code PrinterJob} controlling the current
     * rendering request.
     * @see java.awt.print.Printable
     */
    PrinterJob getPrinterJob();

}
