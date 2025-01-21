/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.ArrayList;
import java.util.Collection;

public class UnsoundInference {

    public static void meth() {
        Object[] objArray = {new Object()};
        ArrayList<String> strList = new ArrayList<String>();
        transferBug(objArray, strList);
        String str = strList.get(0);
    }

    public static <Var> void transferBug(Var[] from, Collection<Var> to) {
        to.add(from[0]);
    }
}
