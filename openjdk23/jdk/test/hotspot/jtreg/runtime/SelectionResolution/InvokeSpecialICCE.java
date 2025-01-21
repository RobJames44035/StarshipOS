/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Test of method selection and resolution cases that
 * generate IncompatibleClassChangeError
 * @library /testlibrary/asm
 * @library /runtime/SelectionResolution/classes
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-VerifyDependencies InvokeSpecialICCE
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import selectionresolution.ClassData;
import selectionresolution.MethodData;
import selectionresolution.Result;
import selectionresolution.SelectionResolutionTest;
import selectionresolution.SelectionResolutionTestCase;
import selectionresolution.Template;

public class InvokeSpecialICCE extends SelectionResolutionTest {

    private static final SelectionResolutionTestCase.Builder initBuilder =
        new SelectionResolutionTestCase.Builder();

    static {
        initBuilder.setResult(Result.ICCE);
    }

    private static final Collection<TestGroup> testgroups =
        Arrays.asList(
                /* invokespecial tests */
                /* resolved method is static*/
                /* Group 170: methodref = expected */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKESPECIAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.CLASS),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PACKAGE,
                                                        MethodData.Access.PROTECTED,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.STATIC),
                                             EnumSet.of(ClassData.Package.SAME)),
                        Template.OverrideAbstractExpectedClass,
                        Template.MethodrefEqualsExpected,
                        Template.IgnoredAbstract,
                        Template.InvokespecialCallsiteCases,
                        Template.ObjectrefAssignableToCallsite),
                /* Group 171: methodref != expected, expected is class */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKESPECIAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.CLASS),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PACKAGE,
                                                        MethodData.Access.PROTECTED,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.STATIC),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedClass,
                        Template.MethodrefNotEqualsExpectedClass,
                        Template.IgnoredAbstract,
                        Template.InvokespecialCallsiteCases,
                        Template.ObjectrefAssignableToCallsite),
                /* Group 172: methodref != expected, expected is interface */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKESPECIAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.INTERFACE),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.STATIC),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedIface,
                        Template.MethodrefNotEqualsExpectedIface,
                        Template.IgnoredAbstract,
                        Template.InvokespecialCallsiteCases,
                        Template.ObjectrefAssignableToCallsite),

                /* Group 173: Ambiguous resolution */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKESPECIAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.INTERFACE),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.allOf(MethodData.Context.class),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedIface,
                        Template.MethodrefAmbiguous,
                        Template.IgnoredAbstract,
                        Template.InvokespecialCallsiteCases,
                        Template.ObjectrefAssignableToCallsite)
            );

    private InvokeSpecialICCE() {
        super(testgroups);
    }

    public static void main(final String... args) {
        new InvokeSpecialICCE().run();
    }
}
