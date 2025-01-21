/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.internal.jfc.model;

// Corresponds to <or>
final class XmlOr extends XmlExpression {

    @Override
    boolean isEntity() {
        return false;
    }

    @Override
    protected Result evaluate() {
        Result result = Result.NULL;
        for (XmlElement e : getProducers()) {
            Result r = e.evaluate();
            if (r.isFalse()) {
                result = Result.FALSE;
            }
            if (r.isTrue()) {
                return Result.TRUE;
            }

        }
        return result;
    }
}
