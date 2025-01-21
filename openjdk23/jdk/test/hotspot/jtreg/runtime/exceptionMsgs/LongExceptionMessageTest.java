/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @summary Test to verify throwing an exception with extra long message does
 *          not cause hang.
 * @bug 8312401
 * @run main/othervm LongExceptionMessageTest
 */

class ClassWithLongExceptionMessage {
  static {
    if (true) throw new AssertionError("lorem ipsum ".repeat(16000));
  }
}

public class LongExceptionMessageTest {
  public static void main(String[] args) {
    try {
      new ClassWithLongExceptionMessage();
    } catch(Throwable t) {}
  }
}

