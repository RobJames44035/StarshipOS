/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jshell.execution;

import java.util.List;
import java.util.Map;
import jdk.jshell.spi.ExecutionControl;
import jdk.jshell.spi.ExecutionControlProvider;
import jdk.jshell.spi.ExecutionEnv;

/**
 * A provider of execution engines which run in the same process as JShell.
 *
 * @author Robert Field
 * @since 9
 */
public class LocalExecutionControlProvider implements ExecutionControlProvider{

    /**
     * Create an instance.  An instance can be used to
     * {@linkplain  #generate generate} an {@link ExecutionControl} instance
     * that executes code in the same process.
     */
    public LocalExecutionControlProvider() {
    }

    /**
     * The unique name of this {@code ExecutionControlProvider}.
     *
     * @return "local"
     */
    @Override
    public String name() {
        return "local";
    }

    /**
     * Create and return the default parameter map for
     * {@code LocalExecutionControlProvider}.
     * {@code LocalExecutionControlProvider} has no parameters.
     *
     * @return an empty parameter map
     */
    @Override
    public Map<String,String> defaultParameters() {
        return ExecutionControlProvider.super.defaultParameters();
    }

    /**
     * Create and return a locally executing {@code ExecutionControl} instance.
     *
     * @param env the execution environment, provided by JShell
     * @param parameters the {@linkplain #defaultParameters()  default} or
     * modified parameter map.
     * @return the execution engine
     */
    @Override
    public ExecutionControl generate(ExecutionEnv env, Map<String, String> parameters) {

        // Create ExecutionControl
        ExecutionControl executionControl = createExecutionControl(env, parameters);

        // Apply any configured class path
        List<String> remoteOptions = env.extraRemoteVMOptions();
        int classPathIndex = remoteOptions.indexOf("--class-path") + 1;
        if (classPathIndex > 0 && classPathIndex < remoteOptions.size()) {
            try {
                executionControl.addToClasspath(remoteOptions.get(classPathIndex));
            } catch (ExecutionControl.ExecutionControlException e) {
                throw new RuntimeException("error configuring class path", e);
            }
        }

        // Done
        return executionControl;
    }

    /**
     * Create a new {@link ExecutionControl} instance.
     *
     * <p>
     * This method is invoked by {@link #generate generate()}.
     *
     * @param env the {@code ExecutionEnv} for which the {@link ExecutionControl} should be created
     * @param parameters the parameters that were passed to {@link #generate generate()}
     * @return the newly created {@code ExecutionControl}
     * @since 22
     */
    public ExecutionControl createExecutionControl(ExecutionEnv env, Map<String, String> parameters) {
        return new LocalExecutionControl(new DefaultLoaderDelegate());
    }
}
