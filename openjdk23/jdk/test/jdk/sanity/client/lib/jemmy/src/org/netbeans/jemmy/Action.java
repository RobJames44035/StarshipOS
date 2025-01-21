/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy;

/**
 *
 * Defines an action to be executed by {@code ActionProducer} instance.
 *
 * @see org.netbeans.jemmy.ActionProducer
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public interface Action<R, P> {

    /**
     * Executes this action.
     *
     * @param obj action argument. This argument might be the method parameter
     * in an invocation of {@code ActionProducer.produceAction(Object)}.
     * @return action result.
     */
    public R launch(P obj);

    /**
     * Returns the description value.
     *
     * @return Action description.
     */
    public String getDescription();
}
