/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4464233
 * @summary raw types versus separate compilation causes type error
 * @author gafter
 *
 * @compile  Hashtable.java
 * @compile  CharScanner.java
 * @compile  RetroLexer.java
 */

package parser;

import util.Hashtable; // this is necessary to reproduce the bug!

public class RetroLexer extends antlr.CharScanner
{
    public RetroLexer() {
        literals.put("Foo", new Integer(5));
    }
}
