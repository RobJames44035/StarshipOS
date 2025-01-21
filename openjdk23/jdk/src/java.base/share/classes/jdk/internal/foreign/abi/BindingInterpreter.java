/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package jdk.internal.foreign.abi;

import java.lang.foreign.SegmentAllocator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BindingInterpreter {

    static void unbox(Object arg, List<Binding> bindings, StoreFunc storeFunc, SegmentAllocator allocator) {
        Deque<Object> stack = new LinkedList<>(); // Use LinkedList as a null-friendly Deque for null segment bases

        stack.push(arg);
        for (Binding b : bindings) {
            b.interpret(stack, storeFunc, null, allocator);
        }
    }

    static Object box(List<Binding> bindings, LoadFunc loadFunc, SegmentAllocator allocator) {
        Deque<Object> stack = new ArrayDeque<>();
        for (Binding b : bindings) {
            b.interpret(stack, null, loadFunc, allocator);
        }
       return stack.pop();
    }

    @FunctionalInterface
    public interface StoreFunc {
        void store(VMStorage storage, Object o);
    }

    @FunctionalInterface
    public interface LoadFunc {
        Object load(VMStorage storage, Class<?> type);
    }
}
