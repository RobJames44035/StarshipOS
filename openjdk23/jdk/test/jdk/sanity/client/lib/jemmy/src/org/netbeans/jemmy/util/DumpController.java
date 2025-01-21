/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.util;

import java.awt.Component;

/**
 *
 * @author shura
 */
public interface DumpController {

    public boolean onComponentDump(Component comp);

    public boolean onPropertyDump(Component comp, String name, String value);

}
