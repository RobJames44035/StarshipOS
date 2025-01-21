/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package javax.xml.stream.events;

public interface Characters extends XMLEvent {
  /**
   * Get the character data of this event
   * @return the character data
   */
  public String getData();

  /**
   * Returns true if this set of Characters
   * is all whitespace.  Whitespace inside a document
   * is reported as CHARACTERS.  This method allows
   * checking of CHARACTERS events to see if they
   * are composed of only whitespace characters
   * @return true if the {@code Characters} are all whitespace, false otherwise
   */
  public boolean isWhiteSpace();

  /**
   * Returns true if this is a CData section.  If this
   * event is CData its event type will be CDATA
   *
   * If javax.xml.stream.isCoalescing is set to true CDATA Sections
   * that are surrounded by non CDATA characters will be reported
   * as a single Characters event. This method will return false
   * in this case.
   * @return true if it is {@code CDATA}, false otherwise
   */
  public boolean isCData();

  /**
   * Return true if this is ignorableWhiteSpace.  If
   * this event is ignorableWhiteSpace its event type will
   * be SPACE.
   * @return true if it is ignorable whitespace, false otherwise
   */
  public boolean isIgnorableWhiteSpace();

}
