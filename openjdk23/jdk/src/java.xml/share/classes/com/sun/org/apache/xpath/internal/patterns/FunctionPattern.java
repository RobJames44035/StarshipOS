/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xpath.internal.patterns;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.utils.QName;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import com.sun.org.apache.xpath.internal.objects.XObject;
import java.util.List;

/**
 * Match pattern step that contains a function.
 * @xsl.usage advanced
 * @LastModified: Oct 2017
 */
public class FunctionPattern extends StepPattern
{
    static final long serialVersionUID = -5426793413091209944L;

  /**
   * Construct a FunctionPattern from a
   * {@link com.sun.org.apache.xpath.internal.functions.Function expression}.
   *
   * NEEDSDOC @param expr
   */
  public FunctionPattern(Expression expr, int axis, int predaxis)
  {

    super(0, null, null, axis, predaxis);

    m_functionExpr = expr;
  }

  /**
   * Static calc of match score.
   */
  public final void calcScore()
  {

    m_score = SCORE_OTHER;

    if (null == m_targetString)
      calcTargetString();
  }

  /**
   * Should be a {@link com.sun.org.apache.xpath.internal.functions.Function expression}.
   *  @serial
   */
  Expression m_functionExpr;

  /**
   * This function is used to fixup variables from QNames to stack frame
   * indexes at stylesheet build time.
   * @param vars List of QNames that correspond to variables.  This list
   * should be searched backwards for the first qualified name that
   * corresponds to the variable reference qname.  The position of the
   * QName in the vector from the start of the vector will be its position
   * in the stack frame (but variables above the globalsTop value will need
   * to be offset to the current stack frame).
   */
  public void fixupVariables(List<QName> vars, int globalsSize)
  {
    super.fixupVariables(vars, globalsSize);
    m_functionExpr.fixupVariables(vars, globalsSize);
  }


  /**
   * Test a node to see if it matches the given node test.
   *
   * @param xctxt XPath runtime context.
   *
   * @return {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NODETEST},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NONE},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NSWILD},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_QNAME}, or
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_OTHER}.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt, int context)
          throws javax.xml.transform.TransformerException
  {

    DTMIterator nl = m_functionExpr.asIterator(xctxt, context);
    XNumber score = SCORE_NONE;

    if (null != nl)
    {
      int n;

      while (DTM.NULL != (n = nl.nextNode()))
      {
        score = (n == context) ? SCORE_OTHER : SCORE_NONE;

        if (score == SCORE_OTHER)
        {
          context = n;

          break;
        }
      }

      // nl.detach();
    }
    nl.detach();

    return score;
  }

  /**
   * Test a node to see if it matches the given node test.
   *
   * @param xctxt XPath runtime context.
   *
   * @return {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NODETEST},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NONE},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NSWILD},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_QNAME}, or
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_OTHER}.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt, int context,
                         DTM dtm, int expType)
          throws javax.xml.transform.TransformerException
  {

    DTMIterator nl = m_functionExpr.asIterator(xctxt, context);
    XNumber score = SCORE_NONE;

    if (null != nl)
    {
      int n;

      while (DTM.NULL != (n = nl.nextNode()))
      {
        score = (n == context) ? SCORE_OTHER : SCORE_NONE;

        if (score == SCORE_OTHER)
        {
          context = n;

          break;
        }
      }

      nl.detach();
    }

    return score;
  }

  /**
   * Test a node to see if it matches the given node test.
   *
   * @param xctxt XPath runtime context.
   *
   * @return {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NODETEST},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NONE},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NSWILD},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_QNAME}, or
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_OTHER}.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {

    int context = xctxt.getCurrentNode();
    DTMIterator nl = m_functionExpr.asIterator(xctxt, context);
    XNumber score = SCORE_NONE;

    if (null != nl)
    {
      int n;

      while (DTM.NULL != (n = nl.nextNode()))
      {
        score = (n == context) ? SCORE_OTHER : SCORE_NONE;

        if (score == SCORE_OTHER)
        {
          context = n;

          break;
        }
      }

      nl.detach();
    }

    return score;
  }

  class FunctionOwner implements ExpressionOwner
  {
    /**
     * @see ExpressionOwner#getExpression()
     */
    public Expression getExpression()
    {
      return m_functionExpr;
    }


    /**
     * @see ExpressionOwner#setExpression(Expression)
     */
    public void setExpression(Expression exp)
    {
        exp.exprSetParent(FunctionPattern.this);
        m_functionExpr = exp;
    }
  }

  /**
   * Call the visitor for the function.
   */
  protected void callSubtreeVisitors(XPathVisitor visitor)
  {
    m_functionExpr.callVisitors(new FunctionOwner(), visitor);
    super.callSubtreeVisitors(visitor);
  }

}
