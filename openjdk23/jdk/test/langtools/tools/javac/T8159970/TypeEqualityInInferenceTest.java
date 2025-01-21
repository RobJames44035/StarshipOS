/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8159970
 * @summary javac, JLS8 18.2.4 is not completely implemented by the compiler
 * @library /tools/lib/types
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.comp
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 *          jdk.compiler/com.sun.tools.javac.file
 * @build TypeHarness
 * @run main TypeEqualityInInferenceTest
 */

import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.Type.UndetVar;
import com.sun.tools.javac.code.Type.UndetVar.InferenceBound;
import com.sun.tools.javac.util.Assert;

public class TypeEqualityInInferenceTest extends TypeHarness {
    StrToTypeFactory strToTypeFactory;

    public static void main(String... args) throws Exception {
        new TypeEqualityInInferenceTest().runAll();
    }

    void runAll() {
        List<String> imports = new ArrayList<>();
        imports.add("java.util.*");
        List<String> typeVars = new ArrayList<>();
        typeVars.add("T");
        strToTypeFactory = new StrToTypeFactory(null, imports, typeVars);

        runTest("List<? extends T>", "List<? extends String>", predef.stringType);
        runTest("List<? extends T>", "List<?>", predef.objectType);
        runTest("List<? super T>", "List<? super String>", predef.stringType);
    }

    void runTest(String freeTypeStr, String typeStr, Type equalityBoundType) {
        Type freeType = strToTypeFactory.getType(freeTypeStr);
        Type aType = strToTypeFactory.getType(typeStr);

        withInferenceContext(strToTypeFactory.getTypeVars(), inferenceContext -> {
            assertSameType(inferenceContext.asUndetVar(freeType), aType);
            UndetVar undetVarForT = (UndetVar)inferenceContext.undetVars().head;
            checkEqualityBound(undetVarForT, equalityBoundType);
        });

        withInferenceContext(strToTypeFactory.getTypeVars(), inferenceContext -> {
            assertSameType(aType, inferenceContext.asUndetVar(freeType));
            UndetVar undetVarForT = (UndetVar)inferenceContext.undetVars().head;
            checkEqualityBound(undetVarForT, equalityBoundType);
        });
    }

    void checkEqualityBound(UndetVar uv, Type boundType) {
        com.sun.tools.javac.util.List<Type> equalBounds = uv.getBounds(InferenceBound.EQ);
        Assert.check(!equalBounds.isEmpty() && equalBounds.length() == 1,
                "undetVar must have only one equality bound");
        Type bound = equalBounds.head;
        Assert.check(bound == boundType, "equal bound must be of type " + boundType);
    }
}
