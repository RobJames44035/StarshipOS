/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import p1.AInterface;
import p3.ServiceInterface;

module m1 {
    exports p1 to m4;

    opens p1 to m4;

    requires transitive java.desktop;
    requires m3;

    provides ServiceInterface with AInterface;
}
