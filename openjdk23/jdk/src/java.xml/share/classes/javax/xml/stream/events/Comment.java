/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package javax.xml.stream.events;

public interface Comment extends XMLEvent {

  /**
   * Return the string data of the comment, returns empty string if it
   * does not exist.
   * @return the string data of the comment
   */
  public String getText();
}
