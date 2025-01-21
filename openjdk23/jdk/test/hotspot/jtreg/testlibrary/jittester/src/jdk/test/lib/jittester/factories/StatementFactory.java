/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester.factories;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.ProductionLimiter;
import jdk.test.lib.jittester.Rule;
import jdk.test.lib.jittester.Statement;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class StatementFactory extends Factory<Statement> {
    private final Rule<IRNode> rule;
    private final boolean needSemicolon;

    StatementFactory(long complexityLimit, int operatorLimit,
            TypeKlass ownerClass, boolean exceptionSafe,
            boolean noconsts, boolean needSemicolon ){
        this.needSemicolon = needSemicolon;
        rule = new Rule<>("statement");
        IRNodeBuilder builder = new IRNodeBuilder()
                .setComplexityLimit(complexityLimit)
                .setOperatorLimit(operatorLimit)
                .setOwnerKlass(ownerClass)
                .setExceptionSafe(exceptionSafe)
                .setNoConsts(noconsts)
                .setResultType(PseudoRandom.randomElement(TypeList.getAll()));
        rule.add("array_creation", builder.getArrayCreationFactory());
        rule.add("assignment", builder.getAssignmentOperatorFactory());
        rule.add("function", builder.getFunctionFactory(), 0.1);
    }

    @Override
    public Statement produce() throws ProductionFailedException {
        ProductionLimiter.setLimit();
        try {
            return new Statement(rule.produce(), needSemicolon);
        } finally {
            ProductionLimiter.setUnlimited();
        }
    }
}
