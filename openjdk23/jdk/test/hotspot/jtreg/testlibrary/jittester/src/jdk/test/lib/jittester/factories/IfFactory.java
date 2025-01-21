/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.Block;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.If;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class IfFactory extends SafeFactory<If> {
    protected final long complexityLimit;
    protected final int statementLimit;
    protected final int operatorLimit;
    protected final boolean canHaveBreaks;
    protected final boolean canHaveContinues;
    protected final boolean canHaveReturn;
    protected final TypeKlass ownerClass;
    protected final Type returnType;
    protected final int level;

    IfFactory(TypeKlass ownerClass, Type returnType, long complexityLimit, int statementLimit,
            int operatorLimit, int level, boolean canHaveBreaks, boolean canHaveContinues,
            boolean canHaveReturn) {
        this.ownerClass = ownerClass;
        this.returnType = returnType;
        this.complexityLimit = complexityLimit;
        this.statementLimit = statementLimit;
        this.operatorLimit = operatorLimit;
        this.level = level;
        this.canHaveBreaks = canHaveBreaks;
        this.canHaveContinues = canHaveContinues;
        this.canHaveReturn = canHaveReturn;
    }

    @Override
    public If sproduce() throws ProductionFailedException {
        // resizeUpChildren(If.IfPart.values().length);
        if (statementLimit > 0 && complexityLimit > 0) {
            long conditionComplLimit = (long) (0.01 * PseudoRandom.random() * (complexityLimit - 1));
            IRNodeBuilder builder = new IRNodeBuilder()
                    .setOwnerKlass(ownerClass)
                    .setOperatorLimit(operatorLimit);
            IRNode condition = builder.setComplexityLimit(conditionComplLimit)
                    .setResultType(TypeList.BOOLEAN)
                    .setExceptionSafe(false)
                    .setNoConsts(false)
                    .getLimitedExpressionFactory()
                    .produce();
            // setChild(If.IfPart.CONDITION.ordinal(), condition);
            long remainder = complexityLimit - 1 - condition.complexity();
            long ifBlockComplLimit = (long) (PseudoRandom.random() * remainder);
            long elseBlockComplLimit = remainder - ifBlockComplLimit;
            int ifBlockLimit = (int) (PseudoRandom.random() * statementLimit);
            int elseBlockLimit = statementLimit - ifBlockLimit;
            If.IfPart controlDeviation;
            if (ifBlockLimit > 0 && elseBlockLimit <= 0) {
                controlDeviation = If.IfPart.THEN;
            } else {
                controlDeviation = PseudoRandom.randomBoolean() ? If.IfPart.THEN : If.IfPart.ELSE;
            }
            if (ifBlockLimit > 0 && ifBlockComplLimit > 0) {
                Block thenBlock;
                builder.setResultType(returnType)
                        .setLevel(level)
                        .setComplexityLimit(ifBlockComplLimit)
                        .setStatementLimit(ifBlockLimit);
                if (controlDeviation == If.IfPart.THEN) {
                    thenBlock = builder.setSubBlock(false)
                            .setCanHaveBreaks(canHaveBreaks)
                            .setCanHaveContinues(canHaveContinues)
                            .setCanHaveReturn(canHaveReturn)
                            .getBlockFactory()
                            .produce();
                } else {
                    thenBlock = builder.setSubBlock(false)
                            .setCanHaveBreaks(false)
                            .setCanHaveContinues(false)
                            .setCanHaveReturn(false)
                            .getBlockFactory()
                            .produce();
                }
                // setChild(If.IfPart.THEN.ordinal(), thenBlock);
                Block elseBlock = null;
                if (elseBlockLimit > 0 && elseBlockComplLimit > 0) {
                    builder.setComplexityLimit(elseBlockComplLimit)
                            .setStatementLimit(elseBlockLimit);
                    if (controlDeviation == If.IfPart.ELSE) {
                        elseBlock = builder.setSubBlock(false)
                            .setCanHaveBreaks(canHaveBreaks)
                            .setCanHaveContinues(canHaveContinues)
                            .setCanHaveReturn(canHaveReturn)
                            .getBlockFactory()
                            .produce();
                    } else {
                        elseBlock = builder.setSubBlock(false)
                            .setCanHaveBreaks(false)
                            .setCanHaveContinues(false)
                            .setCanHaveReturn(false)
                            .getBlockFactory()
                            .produce();
                    }
                }
                // setChild(If.IfPart.ELSE.ordinal(), elseBlock);
                return new If(condition, thenBlock, elseBlock, level);
            }
        }
        throw new ProductionFailedException();
    }
}
