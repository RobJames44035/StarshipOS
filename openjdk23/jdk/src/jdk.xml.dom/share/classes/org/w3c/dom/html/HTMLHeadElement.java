/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.html;

/**
 *  Document head information. See the  HEAD element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 *
 * @since 1.4, DOM Level 2
 */
public interface HTMLHeadElement extends HTMLElement {
    /**
     *  URI designating a metadata profile. See the  profile attribute
     * definition in HTML 4.0.
     */
    public String getProfile();
    public void setProfile(String profile);

}
