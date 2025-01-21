/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.css;

/**
 *  The <code>Counter</code> interface is used to represent any counter or
 * counters function value. This interface reflects the values in the
 * underlying style property.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since 1.4, DOM Level 2
 */
public interface Counter {
    /**
     *  This attribute is used for the identifier of the counter.
     */
    public String getIdentifier();

    /**
     *  This attribute is used for the style of the list.
     */
    public String getListStyle();

    /**
     *  This attribute is used for the separator of the nested counters.
     */
    public String getSeparator();

}
