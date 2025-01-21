/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package javax.xml.stream.events;

import javax.xml.namespace.QName;

public interface Attribute extends XMLEvent {

  /**
   * Returns the QName for this attribute.
   * @return the QName of the attribute
   */
  QName getName();

  /**
   * Gets the normalized value of this attribute.
   * @return the normalized value of the attribute
   */
  public String getValue();

  /**
   * Gets the type of this attribute, default is
   * the String "CDATA".
   * @return the type as a String, default is "CDATA"
   */
  public String getDTDType();

  /**
   * A flag indicating whether this attribute was actually
   * specified in the start-tag of its element, or was defaulted from the schema.
   * @return returns true if this was specified in the start element
   */
  public boolean isSpecified();

}
