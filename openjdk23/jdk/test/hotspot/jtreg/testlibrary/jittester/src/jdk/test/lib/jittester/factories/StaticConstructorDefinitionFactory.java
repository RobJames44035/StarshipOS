/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.SymbolTable;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.VariableInfo;
import jdk.test.lib.jittester.functions.StaticConstructorDefinition;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class StaticConstructorDefinitionFactory extends Factory<StaticConstructorDefinition> {
    private final long complexityLimit;
    private final int statementLimit;
    private final int operatorLimit;
    private final int level;
    private final TypeKlass ownerClass;

    StaticConstructorDefinitionFactory(TypeKlass ownerClass, long complexityLimit,
            int statementLimit, int operatorLimit, int level) {
        this.ownerClass = ownerClass;
        this.complexityLimit = complexityLimit;
        this.statementLimit = statementLimit;
        this.operatorLimit = operatorLimit;
        this.level = level;
    }

    @Override
    public StaticConstructorDefinition produce() throws ProductionFailedException {
        SymbolTable.push();
        IRNode body;
        try {
            SymbolTable.remove(SymbolTable.get("this", VariableInfo.class));
            long complLimit = (long) (PseudoRandom.random() * complexityLimit);
            body = new IRNodeBuilder()
                    .setOwnerKlass(ownerClass)
                    .setResultType(TypeList.VOID)
                    .setComplexityLimit(complLimit)
                    .setStatementLimit(statementLimit)
                    .setOperatorLimit(operatorLimit)
                    .setLevel(level)
                    .setSubBlock(true)
                    .setCanHaveBreaks(false)
                    .setCanHaveContinues(false)
                    .setCanHaveReturn(false)
                    .getBlockFactory()
                    .produce();
        } finally {
            SymbolTable.pop();
        }
        return new StaticConstructorDefinition(body);
    }
}
