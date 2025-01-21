/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import java.util.ArrayList;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.functions.FunctionDeclarationBlock;
import jdk.test.lib.jittester.functions.FunctionInfo;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class FunctionDeclarationBlockFactory extends Factory<FunctionDeclarationBlock> {
    private final int memberFunctionsLimit;
    private final int memberFunctionsArgLimit;
    private final int level;
    private final TypeKlass ownerClass;

    FunctionDeclarationBlockFactory(TypeKlass ownerClass, int memberFunctionsLimit,
            int memberFunctionsArgLimit, int level) {
        this.ownerClass = ownerClass;
        this.memberFunctionsLimit = memberFunctionsLimit;
        this.memberFunctionsArgLimit = memberFunctionsArgLimit;
        this.level = level;
    }

    @Override
    public FunctionDeclarationBlock produce() throws ProductionFailedException {
        ArrayList<IRNode> content = new ArrayList<>();
        int memFunLimit = (int) (PseudoRandom.random() * memberFunctionsLimit);
        if (memFunLimit > 0) {
            IRNodeBuilder builder = new IRNodeBuilder()
                    .setOwnerKlass(ownerClass)
                    .setMemberFunctionsArgLimit(memberFunctionsArgLimit)
                    .setFlags(FunctionInfo.ABSTRACT | FunctionInfo.PUBLIC);
            for (int i = 0; i < memFunLimit; i++) {
                try {
                    content.add(builder.setName("func_" + i).getFunctionDeclarationFactory().produce());
                } catch (ProductionFailedException e) {
                    // TODO: do we have to react here?
                }
            }
        }
        return new FunctionDeclarationBlock(ownerClass, content, level);
    }
}
