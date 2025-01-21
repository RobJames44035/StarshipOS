/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.util.Pair;
import jdk.test.lib.jittester.BinaryOperator;
import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.VariableBase;
import jdk.test.lib.jittester.utils.TypeUtil;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class CompoundArithmeticAssignmentOperatorFactory extends BinaryOperatorFactory {
    CompoundArithmeticAssignmentOperatorFactory(OperatorKind opKind, long complexityLimit,
            int operatorLimit, TypeKlass ownerClass, Type resultType, boolean exceptionSafe, boolean noconsts) {
        super(opKind, complexityLimit, operatorLimit, ownerClass, resultType, exceptionSafe, noconsts);
    }

    @Override
    protected boolean isApplicable(Type resultType) {
        return TypeList.isBuiltIn(resultType) && !resultType.equals(TypeList.BOOLEAN);
    }

    @Override
    protected Pair<Type, Type> generateTypes() {
        return new Pair<>(resultType, PseudoRandom.randomElement(
                TypeUtil.getExplicitlyCastable(TypeList.getBuiltIn(), resultType)));
    }

    @Override
    protected BinaryOperator generateProduction(Type leftType, Type rightType) throws ProductionFailedException {
        long leftComplexityLimit = (long) (PseudoRandom.random() * complexityLimit);
        long rightComplexityLimit = complexityLimit - leftComplexityLimit;
        int leftOperatorLimit = (int) (PseudoRandom.random() * operatorLimit);
        int rightOperatorLimit = operatorLimit = leftOperatorLimit;
        IRNodeBuilder builder = new IRNodeBuilder().setOwnerKlass((TypeKlass) ownerClass)
                .setExceptionSafe(exceptionSafe)
                .setNoConsts(noconsts);
        IRNode rightExpr = builder.setComplexityLimit(rightComplexityLimit)
                .setOperatorLimit(rightOperatorLimit)
                .setResultType(rightType)
                .getExpressionFactory()
                .produce();
        VariableBase leftExpr = builder.setComplexityLimit(leftComplexityLimit)
                .setOperatorLimit(leftOperatorLimit)
                .setResultType(leftType)
                .setIsConstant(false)
                .setIsInitialized(true)
                .getVariableFactory()
                .produce();
        return new BinaryOperator(opKind, resultType, leftExpr, rightExpr);
    }
}
