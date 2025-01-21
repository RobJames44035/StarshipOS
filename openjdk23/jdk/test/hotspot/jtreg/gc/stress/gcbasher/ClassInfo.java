/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc.stress.gcbasher;

import java.util.HashSet;
import java.util.Set;

class ClassInfo {
    private String name;

    private Set<Dependency> staticResolution;
    private Set<Dependency> staticInitialization;
    private Set<Dependency> constructorResolution;
    private Set<Dependency> constructorInitialization;
    private Set<Dependency> methodResolution;
    private Set<Dependency> methodInitialization;

    public ClassInfo(String name) {
        this.name = name;

        staticResolution = new HashSet<>();
        staticInitialization = new HashSet<>();
        constructorResolution = new HashSet<>();
        constructorInitialization = new HashSet<>();
        methodResolution = new HashSet<>();
        methodInitialization = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void addResolutionDep(Dependency d) {
        if(d.getMethodName().equals("<clinit>")) {
            staticResolution.add(d);
        } else if(d.getMethodName().equals("<init>")) {
            constructorResolution.add(d);
        } else {
            methodResolution.add(d);
        }
    }

    public void addInitializationDep(Dependency d) {
        if(d.getMethodName().equals("<clinit>")) {
            staticInitialization.add(d);
        } else if(d.getMethodName().equals("<init>")) {
            constructorInitialization.add(d);
        } else {
            methodInitialization.add(d);
        }
    }
}
