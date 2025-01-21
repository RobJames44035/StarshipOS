/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xpath.internal.functions;

import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.utils.QName;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.axes.SubContextList;
import com.sun.org.apache.xpath.internal.compiler.Compiler;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import com.sun.org.apache.xpath.internal.objects.XObject;
import java.util.List;


/**
 * Execute the Last() function.
 * @xsl.usage advanced
 * @LastModified: Oct 2017
 */
public class FuncLast extends Function
{
    static final long serialVersionUID = 9205812403085432943L;

  private boolean m_isTopLevel;

  /**
   * Figure out if we're executing a toplevel expression.
   * If so, we can't be inside of a predicate.
   */
  public void postCompileStep(Compiler compiler)
  {
    m_isTopLevel = compiler.getLocationPathDepth() == -1;
  }

  /**
   * Get the position in the current context node list.
   *
   * @param xctxt non-null reference to XPath runtime context.
   *
   * @return The number of nodes in the list.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public int getCountOfContextNodeList(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {

    // assert(null != m_contextNodeList, "m_contextNodeList must be non-null");
    // If we're in a predicate, then this will return non-null.
    SubContextList iter = m_isTopLevel ? null : xctxt.getSubContextList();

    // System.out.println("iter: "+iter);
    if (null != iter)
      return iter.getLastPos(xctxt);

    DTMIterator cnl = xctxt.getContextNodeList();
    int count;
    if(null != cnl)
    {
      count = cnl.getLength();
      // System.out.println("count: "+count);
    }
    else
      count = 0;
    return count;
  }

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
    XNumber xnum = new XNumber((double) getCountOfContextNodeList(xctxt));
    // System.out.println("last: "+xnum.num());
    return xnum;
  }

  /**
   * No arguments to process, so this does nothing.
   */
  public void fixupVariables(List<QName> vars, int globalsSize)
  {
    // no-op
  }

}
