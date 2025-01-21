/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.util.Map;
import jdk.jshell.spi.ExecutionControl;
import jdk.jshell.spi.ExecutionControlProvider;
import jdk.jshell.spi.ExecutionEnv;

public class MyExecutionControlProvider implements ExecutionControlProvider {

    private final UserJdiUserRemoteTest test;

    MyExecutionControlProvider(UserJdiUserRemoteTest test) {
        this.test = test;
    }

    @Override
    public String name() {
        return "my";
    }

    @Override
    public ExecutionControl generate(ExecutionEnv env, Map<String, String> parameters) throws Throwable {
        return MyExecutionControl.make(env, test);
    }

}
