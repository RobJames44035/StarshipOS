/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package javax.xml.stream.events;
public interface NotationDeclaration extends XMLEvent {
  /**
   * The notation name.
   * @return the notation name
   */
  String getName();

  /**
   * The notation's public identifier, or null if none was given.
   * @return the public identifier
   */
  String getPublicId();

  /**
   * The notation's system identifier, or null if none was given.
   * @return the system identifier
   */
  String getSystemId();
}
