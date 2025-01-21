/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.*;

public class LambdaNoFuncIntfFlow {
    private void t(Object i) {
        int j = i instanceof ArrayList ? (ArrayList<String>) i : () -> { return null; };
        j = 0;
        Runnable r = () -> t(j);
    }
}
