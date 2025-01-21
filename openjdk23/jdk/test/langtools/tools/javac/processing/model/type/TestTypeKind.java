/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6347716
 * @summary Test TypeKind.isPrimitive
 * @author  Joseph D. Darcy
 * @modules java.compiler
 *          jdk.compiler
 */

import javax.lang.model.type.TypeKind;
import static javax.lang.model.type.TypeKind.*;
import javax.lang.model.util.*;
import java.util.*;

public class TestTypeKind {
    static int testIsPrimitive() {
        int failures = 0;
        // The eight primitive types
        Set<TypeKind> primitives = EnumSet.of(BOOLEAN,  // 1
                                              BYTE,     // 2
                                              CHAR,     // 3
                                              DOUBLE,   // 4
                                              FLOAT,    // 5
                                              INT,      // 6
                                              LONG,     // 7
                                              SHORT);   // 8

        for(TypeKind tk : TypeKind.values()) {
            boolean primitiveness;
            if ((primitiveness=tk.isPrimitive()) != primitives.contains(tk) ) {
                failures++;
                System.err.println("Unexpected isPrimitive value " + primitiveness +
                                   "for " + tk);
            }
        }
        return failures;
    }

    public static void main(String... argv) {
        int failures  = 0;
        failures += testIsPrimitive();
        if (failures > 0)
            throw new RuntimeException();
    }
}
