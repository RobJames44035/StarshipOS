/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.compilercontrol.share.scenario;

import compiler.compilercontrol.share.method.MethodDescriptor;

public class JcmdCommand extends CompileCommand {
    public final Scenario.JcmdType jcmdType;

    public JcmdCommand(Command command,
                       boolean isValid,
                       MethodDescriptor methodDescriptor,
                       Scenario.Compiler compiler,
                       Scenario.Type type,
                       Scenario.JcmdType jcmdType) {
        super(command, isValid, methodDescriptor, compiler, type);
        this.jcmdType = jcmdType;
    }

    public JcmdCommand(Command command,
                       boolean isValid,
                       MethodDescriptor methodDescriptor,
                       Scenario.Compiler compiler,
                       Scenario.Type type,
                       Scenario.JcmdType jcmdType,
                       String argument) {
        super(command, isValid, methodDescriptor, compiler, type, argument);
        this.jcmdType = jcmdType;
    }


    /**
     * Enchances parent's class method with the JCMDtype printing:
     * {@code ... JCMDType: <jcmd_type>}
     */
    protected String formatFields() {
        return super.formatFields() + " JCMDType: " + jcmdType;
    }

}
