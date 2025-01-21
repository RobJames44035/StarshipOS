/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xpath.internal;

import com.sun.org.apache.xpath.internal.functions.FuncExtFunction;
import com.sun.org.apache.xpath.internal.objects.XObject;
import java.util.List;

/**
 * Interface that XPath objects can call to obtain access to an
 * ExtensionsTable.
 *
 * @LastModified: Oct 2017
 */
public interface ExtensionsProvider
{
  /**
   * Is the extension function available?
   */

  public boolean functionAvailable(String ns, String funcName)
          throws javax.xml.transform.TransformerException;

  /**
   * Is the extension element available?
   */
  public boolean elementAvailable(String ns, String elemName)
          throws javax.xml.transform.TransformerException;

  /**
   * Execute the extension function.
   */
  public Object extFunction(String ns, String funcName, List<XObject> argVec,
          Object methodKey)
            throws javax.xml.transform.TransformerException;

  /**
   * Execute the extension function.
   */
  public Object extFunction(FuncExtFunction extFunction, List<XObject> argVec)
            throws javax.xml.transform.TransformerException;
}
