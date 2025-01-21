/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.html;

/**
 *  For the <code>H1</code> to <code>H6</code> elements. See the  H1 element
 * definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 *
 * @since 1.4, DOM Level 2
 */
public interface HTMLHeadingElement extends HTMLElement {
    /**
     *  Horizontal text alignment. See the  align attribute definition in HTML
     * 4.0. This attribute is deprecated in HTML 4.0.
     */
    public String getAlign();
    public void setAlign(String align);

}
