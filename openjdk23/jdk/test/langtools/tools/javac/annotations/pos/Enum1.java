/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4916218
 * @summary JSR175: annotations on enum constant
 * @author gafter
 *
 * @compile Enum1.java
 */

package annotation.enums;

@interface A {}

enum T {
    @A a,
    @A b;
}
