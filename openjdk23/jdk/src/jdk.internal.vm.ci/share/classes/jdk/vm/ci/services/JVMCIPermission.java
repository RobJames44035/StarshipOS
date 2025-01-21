/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.vm.ci.services;

import java.security.BasicPermission;

/**
 * This class represents the permission to access JVMCI services.
 */
public class JVMCIPermission extends BasicPermission {

    private static final long serialVersionUID = 6346818963934448226L;

    public JVMCIPermission() {
        super("jvmci");
    }
}
