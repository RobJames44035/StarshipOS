/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * Simple class for fast lookup of char values, when used with
 * hashtables.  You can set the char, then use it as a key.
 * @xsl.usage internal
 */
public class CharKey extends Object
{

  /** String value          */
  private char m_char;

  /**
   * Constructor CharKey
   *
   * @param key char value of this object.
   */
  public CharKey(char key)
  {
    m_char = key;
  }

  /**
   * Default constructor for a CharKey.
   */
  public CharKey()
  {
  }

  /**
   * Get the hash value of the character.
   *
   * @return hash value of the character.
   */
  public final void setChar(char c)
  {
    m_char = c;
  }



  /**
   * Get the hash value of the character.
   *
   * @return hash value of the character.
   */
  public final int hashCode()
  {
    return (int)m_char;
  }

  /**
   * Override of equals() for this object
   *
   * @param obj to compare to
   *
   * @return True if this object equals this string value
   */
  public final boolean equals(Object obj)
  {
    return ((CharKey)obj).m_char == m_char;
  }
}
