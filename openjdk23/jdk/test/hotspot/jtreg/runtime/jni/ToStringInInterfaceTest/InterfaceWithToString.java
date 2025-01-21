/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * Interface with toString declared.
 */
public interface InterfaceWithToString {

  void someMethod();

  /**
   * Same as Object.toString().
   *
   * @return some custom string.
   */
  String toString();
}
