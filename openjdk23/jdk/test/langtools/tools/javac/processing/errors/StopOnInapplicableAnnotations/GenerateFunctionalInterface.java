/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8014016
 * @summary Ensure that an annotation processor can generate a super-interface
 *          which will make the current interface functional
 * @modules jdk.compiler/com.sun.tools.javac.util
 * @build GenerateSuperInterfaceProcessor
 * @compile -XDaccessInternalAPI -processor GenerateSuperInterfaceProcessor GenerateFunctionalInterface.java
 */

import java.lang.FunctionalInterface;

@FunctionalInterface
@Generate(fileName="SuperInterface.java", content="interface SuperInterface { public void run(); }")
interface GenerateFunctionalInterface extends SuperInterface {
}
