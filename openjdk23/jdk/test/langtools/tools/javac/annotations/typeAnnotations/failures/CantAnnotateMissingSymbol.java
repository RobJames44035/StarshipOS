/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.annotation.*;
import java.util.List;

class CantAnnotateMissingSymbol {
    List<@TA NoSuch> x;
}

@Target(ElementType.TYPE_USE)
@interface TA { }
