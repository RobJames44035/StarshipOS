/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.share.jdi;

import java.util.List;

import vm.mlvm.share.jpda.StratumInfo;

import com.sun.jdi.request.BreakpointRequest;

public class BreakpointInfo {
    public static enum Type {
        /** set breakpoint via BreakpointRequest */
        EXPLICIT,
        /**
         * don't set JDI breakpoint, verify that we can reach the location
         * via stepping
         */
        IMPLICIT
    };

    // Initial information
    public Type type = Type.EXPLICIT;
    public String className = "";
    public final String methodName;
    public int methodLine = 0;

    /** Breakpoint stratum (JSR-045). null == default stratum */
    public StratumInfo stratumInfo = null;

    /**
     * How many times this breakpoint should be hit. null == any number of
     * hits
     */
    public Integer requiredHits = null;

    /** How many steps do via StepRequest after reaching the breakpoint */
    public int stepsToTrace = 0;

    /** Conditional breakpoints should not be enabled by default */
    public final boolean isConditional;

    /** Sub-breakpoints */
    public List<BreakpointInfo> subBreakpoints = null;

    // Fields below are filled in by debugger
    public long bci = -1;
    public BreakpointRequest bpReq = null;
    public int hits = 0;

    public BreakpointInfo(String methodName) {
        this(methodName, false);
    }

    public BreakpointInfo(String methodName, boolean isConditional) {
        this.methodName = methodName;
        this.isConditional = isConditional;
    }

    public boolean isHit() {
        if (requiredHits == null) {
            return hits > 0;
        } else {
            return hits == requiredHits;
        }
    }

    @Override
    public String toString() {
        return className + "." + methodName + ":" + methodLine + "[bci=" + bci + ",bp=" + bpReq + "]";
    }

}
