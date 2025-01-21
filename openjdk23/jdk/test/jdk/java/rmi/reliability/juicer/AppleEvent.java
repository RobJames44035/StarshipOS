/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.io.Serializable;
import java.util.Date;

/**
 * The AppleEvent class is simply an object to be passed to a
 * remote object exported by an applet.  The intent is to verify
 * proper object serialization of arrays.
 */
public class AppleEvent implements Serializable {

    public static final int BUY   = 0;
    public static final int EAT   = 1;
    public static final int THROW = 2;

    private final int what;
    private final Date when;

    public AppleEvent(int what) {
        this.what = what;
        this.when = new Date();
    }

    public String toString() {
        String desc = "[";
        switch (what) {
        case BUY:
            desc += "BUY";
            break;
        case EAT:
            desc += "EAT";
            break;
        case THROW:
            desc += "THROW";
            break;
        }
        desc += " @ " + when + "]";
        return desc;
    }
}
