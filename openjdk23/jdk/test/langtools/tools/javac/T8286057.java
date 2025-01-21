/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class EnumsCantBeGeneric {
  public enum E1<> {}
  public enum E2<T> {}
  public enum E3<T, T> {}
}
