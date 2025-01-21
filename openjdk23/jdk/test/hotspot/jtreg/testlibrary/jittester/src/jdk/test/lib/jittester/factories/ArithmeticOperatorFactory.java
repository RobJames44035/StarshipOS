/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.Operator;
import jdk.test.lib.jittester.OperatorKind;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.Rule;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.types.TypeKlass;

class ArithmeticOperatorFactory extends Factory<Operator> {
    private final Rule<Operator> rule;

    ArithmeticOperatorFactory(long complexityLimit, int operatorLimit, TypeKlass ownerClass,
            Type resultType, boolean exceptionSafe, boolean noconsts) throws ProductionFailedException {
        IRNodeBuilder builder = new IRNodeBuilder()
                .setComplexityLimit(complexityLimit)
                .setOperatorLimit(operatorLimit)
                .setOwnerKlass(ownerClass)
                .setResultType(resultType)
                .setExceptionSafe(exceptionSafe)
                .setNoConsts(noconsts);
        rule = new Rule<>("arithmetic");
        rule.add("add", builder.setOperatorKind(OperatorKind.ADD).getBinaryOperatorFactory());
        rule.add("sub", builder.setOperatorKind(OperatorKind.SUB).getBinaryOperatorFactory());
        rule.add("mul", builder.setOperatorKind(OperatorKind.MUL).getBinaryOperatorFactory());
        if (!exceptionSafe) {
            rule.add("div", builder.setOperatorKind(OperatorKind.DIV).getBinaryOperatorFactory());
            rule.add("mod", builder.setOperatorKind(OperatorKind.MOD).getBinaryOperatorFactory());
        }
        rule.add("unary_plus", builder.setOperatorKind(OperatorKind.UNARY_PLUS).getUnaryOperatorFactory());
        rule.add("unary_minus", builder.setOperatorKind(OperatorKind.UNARY_MINUS).getUnaryOperatorFactory());
    }

    @Override
    public Operator produce() throws ProductionFailedException {
        return rule.produce();
    }
}
