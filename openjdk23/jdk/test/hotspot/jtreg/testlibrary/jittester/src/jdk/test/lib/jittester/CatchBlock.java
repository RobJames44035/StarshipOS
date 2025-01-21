/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.lib.jittester;

import java.util.Collections;
import java.util.List;
import jdk.test.lib.jittester.visitors.Visitor;

public class CatchBlock extends IRNode {
    public final List<Type> throwables;

    public CatchBlock(IRNode body, List<Type> throwables, int level) {
        super(body.getResultType());
        this.level = level;
        this.throwables = Collections.unmodifiableList(throwables);
        addChild(body);
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
