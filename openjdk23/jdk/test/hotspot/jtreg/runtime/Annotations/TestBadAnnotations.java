/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8339192
 * @summary Check that malformed annotations don't cause crashes
 * @compile BadDeprecatedExtraMemberAtEnd.jcod
 *          BadDeprecatedExtraMemberAtStart.jcod
 *          BadDeprecatedSinceWrongType.jcod
 *          BadDeprecatedForRemovalWrongType.jcod
 *          BadDeprecatedForRemovalBadCPIndex.jcod
 *          BadContendedGroupBadCPIndex.jcod
 *          BadContendedGroupWrongType.jcod
 * @modules java.base/jdk.internal.vm.annotation
 * @run main/othervm -XX:-RestrictContended TestBadAnnotations
 */
import java.lang.annotation.Annotation;
import java.lang.reflect.*;

// None of the malformed nnotations should cause assertion failures or
// other crashes, nor exceptions - we simply don't process them. Note that
// even if the annotation is malformed the class will still be marked as having
// that annotation; it is only the "forRemoval" state of @Deprecated, and
// the "group" value of @Contended that is potentially afffected.
// There is no API to query what annotations the VM considers applied
// to a class/field/method, so we don't try to read anything back.

// The testcases defined reflect the changes that were made to the code under
// 8339192 - we do not try to define exhaustive tests for every potential
// malformation. Each of these test case will trigger an assert prior to
// the fix.

public class TestBadAnnotations {
    public static void main(String[] args) throws Throwable {
        Class<?> c = Class.forName("BadDeprecatedExtraMemberAtEnd");
        c = Class.forName("BadDeprecatedExtraMemberAtStart");
        c = Class.forName("BadDeprecatedSinceWrongType");
        c = Class.forName("BadDeprecatedForRemovalWrongType");
        c = Class.forName("BadDeprecatedForRemovalBadCPIndex");
        c = Class.forName("BadContendedGroupBadCPIndex");
        c = Class.forName("BadContendedGroupWrongType");
    }
}
