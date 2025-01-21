/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     5060485
 * @summary The scope of a class type parameter is too wide
 * @author  Peter von der Ah\u00e9
 * @compile Pos.java
 */

public class Pos<X extends String, Y extends X> {
}
