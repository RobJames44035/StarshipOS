/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     5060485
 * @summary The scope of a class type parameter is too wide
 * @compile T5060485.java
 */

public class T5060485<Y> {
    static public class Y {}

    static public class Y1 <T extends T5060485<Y>> {}
}
