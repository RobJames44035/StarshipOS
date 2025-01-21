/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import static java.lang.classfile.TypeAnnotation.TargetType.*;

/*
 * @test
 * @summary Test population of reference info for repeating type annotations
 * @compile -g Driver.java ReferenceInfoUtil.java RepeatingTypeAnnotations.java
 * @run main Driver RepeatingTypeAnnotations
 * @author Werner Dietl
 */
public class RepeatingTypeAnnotations {
    // Field types
    @TADescription(annotation = "RTAs", type = FIELD)
    public String fieldAsPrimitive() {
        return "@RTA @RTA int test;";
    }

    // Method returns
    @TADescription(annotation = "RTAs", type = METHOD_RETURN)
    public String methodReturn1() {
        return "@RTA @RTA int test() { return 0; }";
    }

    @TADescription(annotation = "RTAs", type = METHOD_RETURN)
    public String methodReturn2() {
        return "@RTAs({@RTA, @RTA}) int test() { return 0; }";
    }

    // Method parameters
    @TADescriptions({
        @TADescription(annotation = "RTAs", type = METHOD_FORMAL_PARAMETER,
                paramIndex = 0),
        @TADescription(annotation = "RTBs", type = METHOD_FORMAL_PARAMETER,
                paramIndex = 0,
                genericLocation = { 3, 0 })
    })
    public String methodParam1() {
        return "void m(@RTA @RTA List<@RTB @RTB String> p) {}";
    }

    @TADescriptions({
        @TADescription(annotation = "RTAs", type = METHOD_FORMAL_PARAMETER,
                paramIndex = 0),
        @TADescription(annotation = "RTBs", type = METHOD_FORMAL_PARAMETER,
                paramIndex = 0,
                genericLocation = { 3, 0 })
    })
    public String methodParam2() {
        return "void m(@RTAs({@RTA, @RTA}) List<@RTBs({@RTB, @RTB}) String> p) {}";
    }

    // TODO: test that all other locations work with repeated type annotations.
}
