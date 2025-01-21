/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.io.Serializable ;

/**
 * That class is used to modelize a parameter to be used as MBean property
 * value or MBean operation parameter or returned value.
 */
public class RjmxMBeanParameter implements Serializable {
    public String name = "unset" ;

    public RjmxMBeanParameter() {
    }

    public RjmxMBeanParameter(String name) {
        this.name = name ;
    }

    public boolean equals(Object obj) {
        if ( this.name.equals(((RjmxMBeanParameter)obj).name) ) {
            return true ;
        } else {
            return false ;
        }
    }
}
