/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package javax.xml.stream.events;

import javax.xml.namespace.QName;

public interface Namespace extends Attribute {

  /**
   * Gets the prefix, returns "" if this is a default
   * namespace declaration.
   * @return the prefix
   */
  public String getPrefix();

  /**
   * Gets the uri bound to the prefix of this namespace
   * @return the {@code NamespaceURI}
   */
  public String getNamespaceURI();

  /**
   * returns true if this attribute declares the default namespace
   * @return true if this is default namespace, false otherwise
   */
  public boolean isDefaultNamespaceDeclaration();
}
