/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6346453
 * @summary directSupertypes should return empty list if arg has no supertypes
 * @author  Scott Seligman
 * @library /tools/javac/lib
 * @modules java.compiler
 *          jdk.compiler
 * @build   JavacTestingAbstractProcessor NoSupers
 * @compile -processor NoSupers -proc:only NoSupers.java
 */

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.*;

public class NoSupers extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> tes,
                           RoundEnvironment round) {
        if (round.processingOver()) return true;

        PrimitiveType intType = types.getPrimitiveType(TypeKind.INT);
        if (! types.directSupertypes(intType).isEmpty())
            throw new AssertionError();
        return true;
    }
}
