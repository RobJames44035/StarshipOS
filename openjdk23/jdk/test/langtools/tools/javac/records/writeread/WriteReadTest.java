/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8246774
 * @summary Verify javac can read record classfiles it writes
 * @compile Record.java
 * @compile WriteReadTest.java
 */
public class WriteReadTest {
    Record1 r1;
}
