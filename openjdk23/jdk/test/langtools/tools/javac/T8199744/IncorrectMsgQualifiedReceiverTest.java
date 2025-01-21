/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class IncorrectMsgQualifiedReceiverTest {
    void foo(int any, IncorrectMsgQualifiedReceiverTest IncorrectMsgQualifiedReceiverTest.this) {}
    void bar(int any, IncorrectMsgQualifiedReceiverTest IncorrectMsgQualifiedReceiverTest.this, int another) {}
}
