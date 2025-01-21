/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xpath.internal.functions;

import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import com.sun.org.apache.xpath.internal.objects.XObject;

/**
 * Execute the Count() function.
 * @xsl.usage advanced
 */
public class FuncCount extends FunctionOneArg
{
    static final long serialVersionUID = -7116225100474153751L;

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

//    DTMIterator nl = m_arg0.asIterator(xctxt, xctxt.getCurrentNode());

//    // We should probably make a function on the iterator for this,
//    // as a given implementation could optimize.
//    int i = 0;
//
//    while (DTM.NULL != nl.nextNode())
//    {
//      i++;
//    }
//    nl.detach();
        DTMIterator nl = m_arg0.asIterator(xctxt, xctxt.getCurrentNode());
        int i = nl.getLength();
        nl.detach();

    return new XNumber((double) i);
  }
}
