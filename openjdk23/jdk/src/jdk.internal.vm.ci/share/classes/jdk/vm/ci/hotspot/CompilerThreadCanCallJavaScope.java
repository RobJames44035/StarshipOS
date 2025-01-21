/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.vm.ci.hotspot;

/**
 * Scope used to potentially change whether the current thread can make VM-to-Java calls.
 * A scope is exited by the {@link #close()} method so it should be used in a
 * try-with-resources statement.
 *
 * The scope does nothing if the current thread is not a HotSpot VM CompilerThread
 * for a JVMCI compiler.
 */
public class CompilerThreadCanCallJavaScope implements AutoCloseable {

    /**
     * Thread state used during the scope.
     */
    private final boolean state;

    /**
     * Non-null iff the thread state needs resetting in {@link #close()}.
     */
    private final CompilerToVM vm;

    /**
     * The thread on which the constructor was called.
     */
    private final Thread thread;

    /**
     * Opens a scope to allow/disallow the current thread to make VM-to-Java calls.
     * The scope is a no-op if the current thread is not a HotSpot VM CompilerThread
     * for a JVMCI compiler.
     *
     * @param newState true/false to allow/disallow VM-to-Java calls within the scope
     */
    public CompilerThreadCanCallJavaScope(boolean newState) {
        this.state = newState;
        this.thread = Thread.currentThread();
        CompilerToVM vm = HotSpotJVMCIRuntime.runtime().getCompilerToVM();
        if (vm.updateCompilerThreadCanCallJava(newState)) {
            this.vm = vm;
        } else {
            this.vm = null;
        }
    }

    /**
     * Resets the state of the current thread with respect to whether it can make
     * VM-to-Java calls to what it was before the constructor was called.
     *
     * @throws IllegalStateException if called on a different thread than the constructor
     */
    @Override
    public void close() {
        if (this.thread != Thread.currentThread()) {
            throw new IllegalStateException();
        }

        if (vm != null) {
            vm.updateCompilerThreadCanCallJava(!state);
        }
    }
}
