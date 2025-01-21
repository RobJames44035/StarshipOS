/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package java.util.stream;

import java.util.Spliterator;
import java.util.function.IntFunction;

/**
 * The base type for a stateful test operation.
 */
interface StatefulTestOp<E> extends IntermediateTestOp<E, E> {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static<T> AbstractPipeline chain(AbstractPipeline upstream,
                                            StatefulTestOp op) {
        switch (op.outputShape()) {
            case REFERENCE:
                return new ReferencePipeline.StatefulOp<Object, T>(upstream, op.inputShape(), op.opGetFlags()) {
                    @Override
                    Sink opWrapSink(int flags, Sink sink) {
                        return op.opWrapSink(flags, isParallel(), sink);
                    }

                    @Override
                    <P_IN> Spliterator<T> opEvaluateParallelLazy(PipelineHelper<T> helper,
                                                                 Spliterator<P_IN> spliterator) {
                        return op.opEvaluateParallelLazy(helper, spliterator);
                    }

                    @Override
                    <P_IN> Node<T> opEvaluateParallel(PipelineHelper<T> helper,
                                                      Spliterator<P_IN> spliterator,
                                                      IntFunction<T[]> generator) {
                        return op.opEvaluateParallel(helper, spliterator, generator);
                    }
                };
            case INT_VALUE:
                return new IntPipeline.StatefulOp<Object>(upstream, op.inputShape(), op.opGetFlags()) {
                    @Override
                    Sink opWrapSink(int flags, Sink sink) {
                        return op.opWrapSink(flags, isParallel(), sink);
                    }

                    @Override
                    <P_IN> Spliterator<Integer> opEvaluateParallelLazy(PipelineHelper<Integer> helper,
                                                                 Spliterator<P_IN> spliterator) {
                        return op.opEvaluateParallelLazy(helper, spliterator);
                    }

                    @Override
                    <P_IN> Node<Integer> opEvaluateParallel(PipelineHelper<Integer> helper,
                                                            Spliterator<P_IN> spliterator,
                                                            IntFunction<Integer[]> generator) {
                        return (Node<Integer>) op.opEvaluateParallel(helper, spliterator, generator);
                    }
                };
            case LONG_VALUE:
                return new LongPipeline.StatefulOp<Object>(upstream, op.inputShape(), op.opGetFlags()) {
                    @Override
                    Sink opWrapSink(int flags, Sink sink) {
                        return op.opWrapSink(flags, isParallel(), sink);
                    }

                    @Override
                    <P_IN> Spliterator<Long> opEvaluateParallelLazy(PipelineHelper<Long> helper,
                                                                 Spliterator<P_IN> spliterator) {
                        return op.opEvaluateParallelLazy(helper, spliterator);
                    }

                    @Override
                    <P_IN> Node<Long> opEvaluateParallel(PipelineHelper<Long> helper,
                                                         Spliterator<P_IN> spliterator,
                                                         IntFunction<Long[]> generator) {
                        return (Node<Long>) op.opEvaluateParallel(helper, spliterator, generator);
                    }
                };
            case DOUBLE_VALUE:
                return new DoublePipeline.StatefulOp<Object>(upstream, op.inputShape(), op.opGetFlags()) {
                    @Override
                    Sink opWrapSink(int flags, Sink sink) {
                        return op.opWrapSink(flags, isParallel(), sink);
                    }

                    @Override
                    <P_IN> Spliterator<Double> opEvaluateParallelLazy(PipelineHelper<Double> helper,
                                                                    Spliterator<P_IN> spliterator) {
                        return op.opEvaluateParallelLazy(helper, spliterator);
                    }

                    @Override
                    <P_IN> Node<Double> opEvaluateParallel(PipelineHelper<Double> helper,
                                                           Spliterator<P_IN> spliterator,
                                                           IntFunction<Double[]> generator) {
                        return (Node<Double>) op.opEvaluateParallel(helper, spliterator, generator);
                    }
                };
            default: throw new IllegalStateException(op.outputShape().toString());
        }
    }

    default StreamShape inputShape() { return StreamShape.REFERENCE; }

    default StreamShape outputShape() { return StreamShape.REFERENCE; }

    default int opGetFlags() { return 0; }

    Sink<E> opWrapSink(int flags, boolean parallel, Sink<E> sink);

    @SuppressWarnings("unchecked")
    default <P_IN> Spliterator<E> opEvaluateParallelLazy(PipelineHelper<E> helper,
                                                         Spliterator<P_IN> spliterator) {
        return opEvaluateParallel(helper, spliterator, i -> (E[]) new Object[i]).spliterator();
    }

    <P_IN> Node<E> opEvaluateParallel(PipelineHelper<E> helper,
                                      Spliterator<P_IN> spliterator,
                                      IntFunction<E[]> generator);
}
