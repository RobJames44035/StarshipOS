/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

//

import java.io.*;
import java.util.*;


public class Load {

    private static PrintStream out = System.err;

    public static void main(String[] args) throws Exception {

        boolean installedOnly = false;

        List<String> expected = new ArrayList<String>(Arrays.asList(args));
        if (!expected.isEmpty() && expected.get(0).equals("-i")) {
            expected.remove(0);
            installedOnly = true;
        }
        if (expected.isEmpty())
            throw new Exception("usage: Load [-i] ( fail | provider-class-name )*");

        ServiceLoader<FooService> sl = (installedOnly
                                        ? ServiceLoader.loadInstalled(FooService.class)
                                        : ServiceLoader.load(FooService.class));
        out.format("%s%n", sl);
        Iterator<FooService> sli = sl.iterator();
        Iterator<String> ei = expected.iterator();

        for (;; ei.remove()) {
            FooService fp = null;
            try {
                if (!sli.hasNext())
                    break;
                fp = sli.next();
            } catch (ServiceConfigurationError x) {
                if (ei.next().equals("fail")) {
                    out.format("Failed as expected: %s%n", x);
                    continue;
                }
                throw x;
            }
            String ec = ei.next();
            if (!fp.getClass().getName().equals(ec))
                throw new
                    Exception(String.format("Wrong provider %s; expected %s",
                                            fp.getClass().getName(), ec));
            out.format("Provider found: %s%n", fp.getClass().getName());
        }

        if (ei.hasNext())
            throw new Exception("Missing providers: " + expected);

    }

}
