/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.type.var.may.not.be.followed.by.other.bounds

import java.util.*;

class X<T, U, V extends T & U> { }
