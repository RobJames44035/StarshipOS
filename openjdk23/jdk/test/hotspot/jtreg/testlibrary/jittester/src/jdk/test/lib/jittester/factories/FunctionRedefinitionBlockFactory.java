/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import java.util.ArrayList;
import java.util.Collection;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Symbol;
import jdk.test.lib.jittester.SymbolTable;
import jdk.test.lib.jittester.VariableInfo;
import jdk.test.lib.jittester.functions.FunctionInfo;
import jdk.test.lib.jittester.functions.FunctionRedefinitionBlock;
import jdk.test.lib.jittester.types.TypeKlass;

class FunctionRedefinitionBlockFactory extends Factory<FunctionRedefinitionBlock> {
    private final int statementLimit;
    private final int operatorLimit;
    private final long complexityLimit;
    private final int level;
    private final TypeKlass ownerClass;
    private final Collection<Symbol> functionSet;

    FunctionRedefinitionBlockFactory(Collection<Symbol> functionSet, TypeKlass ownerClass,
            long complexityLimit, int statementLimit, int operatorLimit, int level) {
        this.functionSet = functionSet;
        this.ownerClass = ownerClass;
        this.complexityLimit = complexityLimit;
        this.statementLimit = statementLimit;
        this.operatorLimit = operatorLimit;
        this.level = level;
    }

    @Override
    public FunctionRedefinitionBlock produce() throws ProductionFailedException {
        ArrayList<IRNode> content = new ArrayList<>();
        if (functionSet.size() > 0) {
            long funcComplexity = complexityLimit / functionSet.size();
            IRNodeBuilder builder = new IRNodeBuilder().setOwnerKlass(ownerClass)
                    .setComplexityLimit(funcComplexity)
                    .setStatementLimit(statementLimit)
                    .setOperatorLimit(operatorLimit)
                    .setLevel(level);
            for (Symbol symbol : functionSet) {
                FunctionInfo functionInfo = (FunctionInfo) symbol;
                Symbol thisSymbol = null;
                if ((functionInfo.flags & FunctionInfo.STATIC) > 0) {
                    thisSymbol = SymbolTable.get("this", VariableInfo.class);
                    SymbolTable.remove(thisSymbol);
                }
                try {
                    content.add(builder.setFunctionInfo(functionInfo)
                            .setFlags(functionInfo.flags)
                            .getFunctionRedefinitionFactory()
                            .produce());
                } catch (ProductionFailedException e) {
                    if ((functionInfo.flags & FunctionInfo.STATIC) == 0) {
                        ownerClass.setAbstract();
                    }
                }
                if ((functionInfo.flags & FunctionInfo.STATIC) > 0) {
                    SymbolTable.add(thisSymbol);
                }
            }
        }
        return new FunctionRedefinitionBlock(content, level);
    }
}
