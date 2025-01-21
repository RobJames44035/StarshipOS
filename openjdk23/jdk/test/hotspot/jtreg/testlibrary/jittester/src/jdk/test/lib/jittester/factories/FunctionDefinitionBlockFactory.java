/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import java.util.ArrayList;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.ProductionParams;
import jdk.test.lib.jittester.Symbol;
import jdk.test.lib.jittester.SymbolTable;
import jdk.test.lib.jittester.VariableInfo;
import jdk.test.lib.jittester.functions.FunctionDefinitionBlock;
import jdk.test.lib.jittester.functions.FunctionInfo;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class FunctionDefinitionBlockFactory extends Factory<FunctionDefinitionBlock> {
    private final long complexityLimit;
    private final int statementLimit;
    private final int operatorLimit;
    private final int memberFunctionsLimit;
    private final int memberFunctionsArgLimit;
    private final int initialFlags;
    private final int level;
    private final TypeKlass ownerClass;

    FunctionDefinitionBlockFactory(TypeKlass ownerClass, int memberFunctionsLimit,
            int memberFunctionsArgLimit, long complexityLimit, int statementLimit,
            int operatorLimit, int level, int initialFlags) {
        this.ownerClass = ownerClass;
        this.memberFunctionsLimit = memberFunctionsLimit;
        this.memberFunctionsArgLimit = memberFunctionsArgLimit;
        this.complexityLimit = complexityLimit;
        this.statementLimit = statementLimit;
        this.operatorLimit = operatorLimit;
        this.level = level;
        this.initialFlags = initialFlags;
    }

    @Override
    public FunctionDefinitionBlock produce() throws ProductionFailedException {
        ArrayList<IRNode> content = new ArrayList<>();
        int memFunLimit = (int) (PseudoRandom.random() * memberFunctionsLimit);
        if (memFunLimit > 0) {
            long memFunCompl = complexityLimit / memFunLimit;
            IRNodeBuilder builder = new IRNodeBuilder().setOwnerKlass(ownerClass)
                    .setComplexityLimit(memFunCompl)
                    .setStatementLimit(statementLimit)
                    .setOperatorLimit(operatorLimit)
                    .setMemberFunctionsArgLimit(memberFunctionsArgLimit)
                    .setLevel(level);
            for (int i = 0; i < memFunLimit; i++) {
                int flags = initialFlags;
                if (PseudoRandom.randomBoolean()) {
                    flags |= FunctionInfo.STATIC;
                }
                if (!ProductionParams.disableFinalMethods.value() && PseudoRandom.randomBoolean()) {
                    flags |= FunctionInfo.FINAL;
                }
                if (PseudoRandom.randomBoolean()) {
                    flags |= FunctionInfo.NONRECURSIVE;
                }
                if (PseudoRandom.randomBoolean()) {
                    flags |= FunctionInfo.SYNCHRONIZED;
                }
                switch ((int) (PseudoRandom.random() * 4)) {
                    case 0:
                        flags |= FunctionInfo.PRIVATE;
                        break;
                    case 1:
                        flags |= FunctionInfo.PROTECTED;
                        break;
                    case 2:
                        flags |= FunctionInfo.DEFAULT;
                        break;
                    case 3:
                        flags |= FunctionInfo.PUBLIC;
                        break;
                }
                Symbol thisSymbol = null;
                if ((flags & FunctionInfo.STATIC) > 0) {
                    thisSymbol = SymbolTable.get("this", VariableInfo.class);
                    SymbolTable.remove(thisSymbol);
                }
                try {
                    content.add(builder.setName("func_" + i)
                            .setFlags(flags)
                            .getFunctionDefinitionFactory()
                            .produce());
                } catch (ProductionFailedException e) {
                }
                if ((flags & FunctionInfo.STATIC) > 0) {
                    SymbolTable.add(thisSymbol);
                }
            }
        }
        return new FunctionDefinitionBlock(content, level, ownerClass);
    }
}
