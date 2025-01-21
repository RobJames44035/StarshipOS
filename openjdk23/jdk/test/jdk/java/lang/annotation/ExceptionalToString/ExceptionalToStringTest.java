/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug     5040830
 * @summary Verify information annotation strings with exception proxies
 * @compile -sourcepath version1 version1/Fleeting.java  version1/Utopia.java version1/DangerousAnnotation.java
 * @compile  AnnotationHost.java
 * @build ExceptionalToStringTest
 * @run main ExceptionalToStringTest
 * @clean Utopia DangerousAnnotation
 * @compile -sourcepath version2 -implicit:none version2/Utopia.java
 * @compile -sourcepath version2 -implicit:none version2/DangerousAnnotation.java
 * @clean Fleeting
 * @run main ExceptionalToStringTest
 */

/**
 * There are three potential exception conditions which can occur
 * reading annotations:
 *
 * EnumConstantNotPresentException - "Thrown when an application tries
 * to access an enum constant by name and the enum type contains no
 * constant with the specified name."
 *
 * AnnotationTypeMismatchException - "Thrown to indicate that a
 * program has attempted to access an element of an annotation whose
 * type has changed after the annotation was compiled (or serialized)"
 *
 * TypeNotPresentException - "Thrown when an application tries to
 * access a type using a string representing the type's name, but no
 * definition for the type with the specified name can be found."
 *
 * The test reads an annotation, DangerousAnnotation, which can
 * display all three pathologies. The pathologies are <em>not</em>
 * present when the version1 sources are used but are present with the
 * version2 sources. The version2 sources remove an enum constant
 * (EnumConstantNotPresentException), change the return type of an
 * annotation method (AnnotationTypeMismatchException), and remove a
 * type whose Class literal is referenced (TypeNotPresentException).
 */
public class ExceptionalToStringTest {
    public static void main(String... args) {
        String annotationAsString = AnnotationHost.class.getAnnotation(DangerousAnnotation.class).toString();

        // Verify no occurrence of "ExceptionProxy" in the string.
        if (annotationAsString.indexOf("ExceptionProxy") != -1) {
            throw new RuntimeException();
        }
    }
}
