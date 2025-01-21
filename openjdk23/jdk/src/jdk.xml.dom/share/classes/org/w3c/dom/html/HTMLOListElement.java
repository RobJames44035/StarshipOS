/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.html;

/**
 *  Ordered list. See the  OL element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 *
 * @since 1.4, DOM Level 2
 */
public interface HTMLOListElement extends HTMLElement {
    /**
     *  Reduce spacing between list items. See the  compact attribute
     * definition in HTML 4.0. This attribute is deprecated in HTML 4.0.
     */
    public boolean getCompact();
    public void setCompact(boolean compact);

    /**
     *  Starting sequence number. See the  start attribute definition in HTML
     * 4.0. This attribute is deprecated in HTML 4.0.
     */
    public int getStart();
    public void setStart(int start);

    /**
     *  Numbering style. See the  type attribute definition in HTML 4.0. This
     * attribute is deprecated in HTML 4.0.
     */
    public String getType();
    public void setType(String type);

}
