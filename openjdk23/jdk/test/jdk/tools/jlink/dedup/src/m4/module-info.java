/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import p3.ServiceInterface;

module m4 {
    requires m3;
    requires transitive java.desktop;
    uses ServiceInterface;
}
