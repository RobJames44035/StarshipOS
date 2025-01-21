/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package com.sun.java_cup.internal.runtime;

/**
 * Defines the Scanner interface, which CUP uses in the default
 * implementation of <code>lr_parser.scan()</code>.  Integration
 * of scanners implementing <code>Scanner</code> is facilitated.
 *
 * @author David MacMahon <davidm@smartsc.com>
 */

/* *************************************************
  Interface Scanner

  Declares the next_token() method that should be
  implemented by scanners.  This method is typically
  called by lr_parser.scan().
 ***************************************************/
public interface Scanner {
    public Symbol next_token() throws java.lang.Exception;
}
