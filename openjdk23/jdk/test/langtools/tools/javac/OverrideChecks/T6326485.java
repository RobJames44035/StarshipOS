/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6326485
 * @summary Compiler does not enforce rule that interfaces may not use Override annotation
 * @author  Peter von der Ah\u00e9
 * @compile T6326485.java
 */

public interface T6326485 {
    @Override
    String toString();
}
