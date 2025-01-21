/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package javax.xml.xpath;

import java.util.List;

/**
 * <p><code>XPathFunction</code> provides access to XPath functions.</p>
 *
 * <p>Functions are identified by QName and arity in XPath.</p>
 *
 * @author  Norman Walsh
 * @author  Jeff Suttor
 * @since 1.5
 */
public interface XPathFunction {
  /**
   * <p>Evaluate the function with the specified arguments.</p>
   *
   * <p>To the greatest extent possible, side-effects should be avoided in the
   * definition of extension functions. The implementation evaluating an
   * XPath expression is under no obligation to call extension functions in
   * any particular order or any particular number of times.</p>
   *
   * @param args The arguments, <code>null</code> is a valid value.
   *
   * @return The result of evaluating the <code>XPath</code> function as an <code>Object</code>.
   *
   * @throws XPathFunctionException If <code>args</code> cannot be evaluated with this <code>XPath</code> function.
   */
  public Object evaluate(List<?> args)
    throws XPathFunctionException;
}
