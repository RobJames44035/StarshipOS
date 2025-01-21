/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug     6593082
 * @summary MirroredTypeException constructor should not accept null
 * @author  Joseph D. Darcy
 * @modules java.compiler
 *          jdk.compiler
 */

import javax.lang.model.type.*;

public class NpeTest  {
    public static void main(String... args) {
        try {
            MirroredTypeException mte = new MirroredTypeException(null);
            throw new RuntimeException("Expected NPE not thrown.");
        } catch (NullPointerException npe) {
            ; // success
        }
    }
}
