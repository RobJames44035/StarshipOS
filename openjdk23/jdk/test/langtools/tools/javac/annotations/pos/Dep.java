/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4903501
 * @summary Please add annotation <at>Deprecated to supplant the javadoc tag
 * @author gafter
 *
 * @compile -Xlint:dep-ann -Werror Dep.java
 */

/** @deprecated */
@Deprecated
class Dep {
}
