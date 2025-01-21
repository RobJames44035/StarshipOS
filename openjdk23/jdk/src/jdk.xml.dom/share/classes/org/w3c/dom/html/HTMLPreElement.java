/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.html;

/**
 *  Preformatted text. See the  PRE element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 *
 * @since 1.4, DOM Level 2
 */
public interface HTMLPreElement extends HTMLElement {
    /**
     *  Fixed width for content. See the  width attribute definition in HTML
     * 4.0. This attribute is deprecated in HTML 4.0.
     */
    public int getWidth();
    public void setWidth(int width);

}
