/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.views;

/**
 * A base interface that all views shall derive from.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Views-20001113'>Document Object Model (DOM) Level 2 Views Specification</a>.
 * @since 1.8, DOM Level 2
 */
public interface AbstractView {
    /**
     * The source <code>DocumentView</code> of which this is an
     * <code>AbstractView</code>.
     */
    public DocumentView getDocument();

}
