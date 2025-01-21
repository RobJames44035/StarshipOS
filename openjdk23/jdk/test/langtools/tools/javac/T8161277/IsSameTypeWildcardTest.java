/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8161277
 * @summary javax.lang.model.util.Types.isSameType(...) returns true on wildcards
 * @library /tools/lib/types
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.comp
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.model
 * @build TypeHarness
 * @run main IsSameTypeWildcardTest
 */

import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.util.Assert;
import com.sun.tools.javac.model.JavacTypes;

public class IsSameTypeWildcardTest extends TypeHarness {
    StrToTypeFactory strToTypeFactory;
    JavacTypes javacTypes;

    public static void main(String... args) throws Exception {
        new IsSameTypeWildcardTest().runTest();
    }

    public IsSameTypeWildcardTest() {
        javacTypes = JavacTypes.instance(context);
    }

    void runTest() {
        List<String> imports = new ArrayList<>();
        imports.add("java.util.*");
        strToTypeFactory = new StrToTypeFactory(null, imports, null);

        Type listOfWildcard = strToTypeFactory.getType("List<?>");
        com.sun.tools.javac.util.List<Type> arguments = listOfWildcard.getTypeArguments();
        Assert.check(!javacTypes.isSameType(arguments.head, arguments.head),
                "if any argument is a wildcard then result must be false");
    }
}
