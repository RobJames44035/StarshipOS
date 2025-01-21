/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.views;

/**
 * The <code>DocumentView</code> interface is implemented by
 * <code>Document</code> objects in DOM implementations supporting DOM
 * Views. It provides an attribute to retrieve the default view of a
 * document.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Views-20001113'>Document Object Model (DOM) Level 2 Views Specification</a>.
 * @since 1.8, DOM Level 2
 */
public interface DocumentView {
    /**
     * The default <code>AbstractView</code> for this <code>Document</code>,
     * or <code>null</code> if none available.
     */
    public AbstractView getDefaultView();

}
