/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.html;

/**
 *  Document base URI. See the  BASE element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 *
 * @since 1.4, DOM Level 2
 */
public interface HTMLBaseElement extends HTMLElement {
    /**
     *  The base URI. See the  href attribute definition in HTML 4.0.
     */
    public String getHref();
    public void setHref(String href);

    /**
     *  The default target frame. See the  target attribute definition in HTML
     * 4.0.
     */
    public String getTarget();
    public void setTarget(String target);

}
