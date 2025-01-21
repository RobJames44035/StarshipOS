/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package pkg;
public abstract class Abstract {
    /**
     * @throws java.lang.NullPointerException this should appear
     * @throws java.lang.IndexOutOfBoundsException this <em>should not for bug-compatibility</em>
     */
    abstract void method() throws NullPointerException;

    // NOTE: Not sure why this test suggests that IndexOutOfBoundsException
    // should not appear due to compatibility with some buggy behavior.
    //
    // Here's the expected behavior: documentation for an exception X is never
    // inherited by an overrider unless it "pulls" it by either (or both)
    // of these:
    //
    //   * tag:
    //       @throws X {@inheritDoc}
    //   * clause:
    //       throws ..., X,...
    //
    // Neither of those are applicable here. Even taking into account
    // mechanisms such as the one introduced in 4947455, neither of
    // NullPointerException and IndexOutOfBoundsException is a subclass
    // of the other.
    //
    // So, IndexOutOfBoundsException should not appear in Extender.
}
