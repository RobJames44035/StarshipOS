/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */
package com.sun.jndi.toolkit.dir;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
  * Is implemented by classes that can perform filter checks on
  * an attribute set.
  */

public interface AttrFilter {

    /**
      * Determines whether an attribute passes the filter.
      */
    public boolean check(Attributes targetAttrs) throws NamingException;
}
