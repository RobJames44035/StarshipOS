/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xpath.internal;

/**
 * Class to let us know when it's time to do
 * a search from the parent because of indexing.
 * @xsl.usage internal
 */
public class FoundIndex extends RuntimeException
{
    static final long serialVersionUID = -4643975335243078270L;

  /**
   * Constructor FoundIndex
   *
   */
  public FoundIndex(){}
}
