/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * Implementation of InterfaceWithToString.
 */
public class ImplementationOfWithToString implements InterfaceWithToString {

  /**
   * @see InterfaceWithToString#someMethod()
   * {@inheritDoc}
   */
  @Override
  public void someMethod() {
    // May do something here.
  }

  /**
   * @see java.lang.Object#toString()
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "toString() from " + getClass().getName();
  }
}
