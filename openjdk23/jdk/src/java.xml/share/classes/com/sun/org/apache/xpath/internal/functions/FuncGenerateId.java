/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xpath.internal.functions;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.objects.XString;

/**
 * Execute the GenerateId() function.
 * @xsl.usage advanced
 */
public class FuncGenerateId extends FunctionDef1Arg
{
    static final long serialVersionUID = 973544842091724273L;

  /**
   * Execute the function.  The function must return
   * a valid object.
   * @param xctxt The current execution context.
   * @return A valid XObject.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt) throws javax.xml.transform.TransformerException
  {

    int which = getArg0AsNode(xctxt);

    if (DTM.NULL != which)
    {
      // Note that this is a different value than in previous releases
      // of Xalan. It's sensitive to the exact encoding of the node
      // handle anyway, so fighting to maintain backward compatability
      // really didn't make sense; it may change again as we continue
      // to experiment with balancing document and node numbers within
      // that value.
      return new XString("N" + Integer.toHexString(which).toUpperCase());
    }
    else
      return XString.EMPTYSTRING;
  }
}
