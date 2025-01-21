/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.utils.res;

//
//  LangResources_it.properties
//

/**
 * The Italian resource bundle.
 * @xsl.usage internal
 */
public class XResources_it extends XResourceBundle
{
  private static final Object[][] _contents = new Object[][]
  {
    { "ui_language", "it" }, { "help_language", "it" }, { "language", "it" },
    { "alphabet", new CharArrayWrapper(
      new char[]{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                  'Y', 'Z' }) },
    { "tradAlphabet", new CharArrayWrapper(
      new char[]{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                  'Y', 'Z' }) },

    //language orientation
    { "orientation", "LeftToRight" },

    //language numbering
    { "numbering", "additive" },

    // largest numerical value
    //{"MaxNumericalValue", new Integer()},
    //These would not be used for EN. Only used for traditional numbering
    //{"numberGroups", new int[]{10,1}},
    //These only used for mutiplicative-additive numbering
    //{"multiplier", "10"},
    //{"multiplierChar", "M"},
    //{"digits", new char[]{'a','b','c','d','e','f','g','h','i'}},
    //{"digits", new char[]{0x10D0,0x10D1,0x10D2,0x10D3,0x10D4,0x10D5,0x10D6,0x10D7,0x10D8}},
    //{"tens", new char[]{0x10D9,0x10DA,0x10DB,0x10DC,0x10DD,0x10DE,0x10DF,0x10E0,0x10E1}},
    //hundreds, etc...
    //{"tables", new String[]{"tens", "digits"}}
  };

  /**
   * Get the association list.
   *
   * @return The association list.
   */
  public Object[][] getContents()
  {
    return _contents;
  }
}
