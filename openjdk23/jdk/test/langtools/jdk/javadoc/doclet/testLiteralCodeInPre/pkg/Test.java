/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package pkg;

/** */
public class Test {
    /**
     * abc{@code def}ghi
     */
    public void no_pre() { }

    /**
     * abc{@code  def  }ghi
     */
    public void no_pre_extra_whitespace() { }

    /**
     * <pre> abc{@code  def  }ghi</pre>
     */
    public void in_pre() { }

    /**
     * xyz <pre> abc{@code  def  }ghi</pre>
     */
    public void pre_after_text() { }

    /**
     * xyz <pre> pqr </pre> abc{@code  def  }ghi
     */
    public void after_pre() { }

    /**
     * xyz <pre> pqr </pre> mno <pre> abc{@code  def  }ghi</pre>
     */
    public void back_in_pre() { }

    /**
     * Lorem ipsum dolor sit amet, consectetur adipiscing elit.
     * Example:  <pre>{@code
     *   line 0 @Override
     *   line 1 <T> void m(T t) {
     *   line 2     // do something with T
     *   line 3 }
     * }</pre>
     * and so it goes.
     */
    public void typical_usage_code() { }

    /**
     * Lorem ipsum dolor sit amet, consectetur adipiscing elit.
     * Example:  <pre>{@literal
     *   line 0 @Override
     *   line 1 <T> void m(T t) {
     *   line 2     // do something with T
     *   line 3 }
     * }</pre>
     * and so it goes.
     */
    public void typical_usage_literal() { }

    /**
     * Lorem ipsum dolor sit amet, consectetur adipiscing elit.
     * Example:  <pre>{@literal
     *   line 0 @Override
     *   line 1 <T> void m(T t) {
     *   line 2     // do something with T
     *   line 3 } }</pre>
     * and so it goes.
     */
    public void recommended_usage_literal() { }

    /**
     * abc {@code
     */
    public void bad_code_no_content() { }

    /**
     * abc {@code abc
     */
    public void bad_code_content() { }

    /**
     * Test for html in pre, note the spaces
     * <PRE>
     * <b   >id           </b   >
     * </PRE>
     */
    public void htmlAttrInPre() {}

    /**
     * More html tag outliers.
     * <pre>
     * {@literal @}Override
     * <code> some.function() </code>
     * </pre>
     *
     *
     */
    public void htmlAttrInPre1() {}
}
