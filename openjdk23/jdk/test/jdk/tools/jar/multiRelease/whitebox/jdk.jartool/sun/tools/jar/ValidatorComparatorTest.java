/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @summary White-box test for Validator.ENTRYNAME_COMPARATOR ( currently just
 *          checks module descriptors ).
 */
package sun.tools.jar;

import java.util.List;
import static java.util.stream.Collectors.toList;
import static sun.tools.jar.Main.ENTRYNAME_COMPARATOR;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidatorComparatorTest {

    @Test
    public void testModuleInfo() throws Throwable {
        List<String> list =
                List.of("module-info.class",
                        "META-INF/versions/9/module-info.class",
                        "META-INF/versions/10/module-info.class");
        List<String> sorted = list.stream()
                .sorted(ENTRYNAME_COMPARATOR)
                .collect(toList());
        List<String> expected = list;
        Assert.assertEquals(sorted, expected);


        list =  List.of("META-INF/versions/10/module-info.class",
                        "META-INF/versions/9/module-info.class",
                        "module-info.class");
        sorted = list.stream().sorted(ENTRYNAME_COMPARATOR).collect(toList());
        expected =
                List.of("module-info.class",
                        "META-INF/versions/9/module-info.class",
                        "META-INF/versions/10/module-info.class");
        Assert.assertEquals(sorted, expected);


        list =  List.of("META-INF/versions/1001/module-info.class",
                        "META-INF/versions/1000/module-info.class",
                        "META-INF/versions/999/module-info.class",
                        "META-INF/versions/101/module-info.class",
                        "META-INF/versions/100/module-info.class",
                        "META-INF/versions/99/module-info.class",
                        "META-INF/versions/31/module-info.class",
                        "META-INF/versions/30/module-info.class",
                        "META-INF/versions/29/module-info.class",
                        "META-INF/versions/21/module-info.class",
                        "META-INF/versions/20/module-info.class",
                        "META-INF/versions/13/module-info.class",
                        "META-INF/versions/12/module-info.class",
                        "META-INF/versions/11/module-info.class",
                        "META-INF/versions/10/module-info.class",
                        "META-INF/versions/9/module-info.class",
                        "module-info.class");
        sorted = list.stream().sorted(ENTRYNAME_COMPARATOR).collect(toList());
        expected =
                List.of("module-info.class",
                        "META-INF/versions/9/module-info.class",
                        "META-INF/versions/10/module-info.class",
                        "META-INF/versions/11/module-info.class",
                        "META-INF/versions/12/module-info.class",
                        "META-INF/versions/13/module-info.class",
                        "META-INF/versions/20/module-info.class",
                        "META-INF/versions/21/module-info.class",
                        "META-INF/versions/29/module-info.class",
                        "META-INF/versions/30/module-info.class",
                        "META-INF/versions/31/module-info.class",
                        "META-INF/versions/99/module-info.class",
                        "META-INF/versions/100/module-info.class",
                        "META-INF/versions/101/module-info.class",
                        "META-INF/versions/999/module-info.class",
                        "META-INF/versions/1000/module-info.class",
                        "META-INF/versions/1001/module-info.class");
        Assert.assertEquals(sorted, expected);
    }
}
