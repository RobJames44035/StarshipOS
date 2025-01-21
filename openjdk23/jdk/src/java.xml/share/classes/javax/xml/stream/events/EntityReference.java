/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package javax.xml.stream.events;
public interface EntityReference extends XMLEvent {

  /**
   * Return the declaration of this entity.
   * @return the declaration
   */
  EntityDeclaration getDeclaration();

  /**
   * The name of the entity
   * @return the entity's name, may not be null
   */
  String getName();
}
