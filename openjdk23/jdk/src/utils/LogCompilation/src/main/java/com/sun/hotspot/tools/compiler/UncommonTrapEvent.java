/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */
package com.sun.hotspot.tools.compiler;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an uncommon trap encountered during a compilation.
 */
class UncommonTrapEvent extends BasicLogEvent {

    private final String reason;
    private final String action;

    /**
     * Denote how many times this trap has been encountered.
     */
    private int count;

    /**
     * The name of the bytecode instruction at which the trap occurred.
     */
    private String bytecode;

    private List<String> jvmsMethods = new ArrayList<>();

    private List<Integer> jvmsBCIs = new ArrayList<>();

    UncommonTrapEvent(double s, String i, String r, String a, int c) {
        super(s, i);
        reason = r;
        action = a;
        count = c;
    }

    public void updateCount(UncommonTrapEvent trap) {
        setCount(Math.max(getCount(), trap.getCount()));
    }

    public String toString() {
        return "uncommon trap " + bytecode + " " + getReason() + " " + getAction();
    }

    public void print(PrintStream stream, boolean printID) {
        if (printID) {
            stream.print(getId() + " ");
        }
        stream.printf("uncommon trap %s %s %s\n", bytecode, getReason(), getAction());
        int indent = 2;
        for (int j = 0; j < jvmsMethods.size(); j++) {
            for (int i = 0; i < indent; i++) {
                stream.print(' ');
            }
            stream.println("@ " + jvmsBCIs.get(j) + " " + jvmsMethods.get(j));
            indent += 2;
        }
    }


    public String getReason() {
        return reason;
    }

    public String getAction() {
        return action;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private boolean trapReasonsAreEqual(String otherReason) {
        if (otherReason.equals(getReason())) {
            return true;
        }

        // Optimization may combine 2 if's into 1
        if (otherReason.equals("unstable_if")
                && getReason().equals("unstable_fused_if")) {
            return true;
        }

        return false;
    }

    /**
     * Set the compilation for this event. This involves identifying the call
     * site to which this uncommon trap event belongs. In addition to setting
     * the {@link #compilation} link, this method will consequently also set
     * the {@link #bytecode} field.
     */
    public void setCompilation(Compilation compilation) {
        super.setCompilation(compilation);

        if (compilation.getSpecial() != null) {
          assert compilation.getLevel() == 0 : "Should be 0";
          return;
        }

        // Attempt to associate a bytecode with with this trap
        CallSite site = compilation.getCall();
        int i = 0;
        try {
            List<UncommonTrap> traps = site.getTraps();
            while (i + 1 < jvmsMethods.size()) {
                if (!jvmsMethods.get(i).equals(site.getMethod().getFullName())) {
                    throw new InternalError(jvmsMethods.get(i) + " != " + site.getMethod().getFullName());
                }
                CallSite result = null;
                for (CallSite call : site.getCalls()) {
                    if (call.getBci() == jvmsBCIs.get(i) &&
                        call.getMethod().getFullName().equals(jvmsMethods.get(i + 1)) &&
                        call.getReceiver() == null) {
                        result = call;
                        i++;
                        break;
                    }
                }
                if (result == null) {
                    throw new InternalError("couldn't find call site");
                }
                site = result;
                traps = site.getTraps();
            }
            for (UncommonTrap trap : traps) {
                if (trap.getBCI() == jvmsBCIs.get(i) &&
                    trapReasonsAreEqual(trap.getReason()) &&
                    trap.getAction().equals(getAction())) {
                    bytecode = trap.getBytecode();
                    return;
                }
            }
            throw new InternalError("couldn't find bytecode for [" + this + "] in Compilation:" + compilation);

        } catch (Exception e) {
            bytecode = "<unknown>";
        }
    }

    public void addMethodAndBCI(String method, int bci) {
        jvmsMethods.add(0, method);
        jvmsBCIs.add(0, bci);
    }

}
