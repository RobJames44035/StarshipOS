/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary check that default methods don't cause ClassReader to complete classes recursively
 * @author  Maurizio Cimadamore
 * @compile pkg/Foo.java
 * @compile ClassReaderTest.java
 */

abstract class ClassReaderTest implements pkg.Foo {}
