/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

import java.util.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class StrCodingBenchmarkDB extends StrCodingBenchmark {


    public static void main(String[] args) throws Throwable {
        final int itrs = Integer.getInteger("iterations", 100000);
        //final int itrs = Integer.getInteger("iterations", 12);
        final int size       = Integer.getInteger("size", 2048);
        final int subsize    = Integer.getInteger("subsize", 128);
        final int maxchar    = Integer.getInteger("maxchar", 128);
        final String regex = System.getProperty("filter");
        final Pattern filter = (regex == null) ? null : Pattern.compile(regex);
        final Random rnd = new Random();

        String[] csns = new String[] {
            "Big5",
            "Johab",
            "EUC_CN",
            "EUC_KR",
            "MS932",
            "MS936",
            "MS949",
            "MS950",
            "GBK",

            "Big5_HKSCS",
            "Big5_HKSCS_2001",
            "Big5_Solaris",
            "MS950_HKSCS",
            "MS950_HKSCS_XP",
            "IBM1364",
            "IBM1381",
            "IBM1383",
            "IBM930",
            "IBM933",
            "IBM935",
            "IBM937",
            "IBM939",
            "IBM942",
            "IBM943",
            "IBM948",
            "IBM949",
            "IBM950",
            "IBM970",
        };

        ArrayList<long[]> sum = new ArrayList<>();

        for (final String csn : csns) {
            final Charset cs = Charset.forName(csn);
            List<Integer> cps = new ArrayList<>(0x4000);
            int off = 0;
            int cp = 0;
            int n = 0;
            CharsetEncoder enc = cs.newEncoder();
            while (cp < 0x10000 && n < cps.size()) {
                if (enc.canEncode((char)cp)) {
                    cps.add(cp);
                    n++;
                }
                cp++;
            }
            Collections.shuffle(cps);
            char[] ca = new char[cps.size()];
            for (int i = 0; i < cps.size(); i++)
                ca[i] = (char)(int)cps.get(i);


            System.out.printf("%n--------%s---------%n", csn);
            for (int sz = 8; sz <= 2048; sz *= 2) {
                System.out.printf("   [len=%d]%n", sz);

                final char[] chars  = Arrays.copyOf(ca, sz);
                final String str = new String(chars);
                final byte[] bs  = str.getBytes(cs);

                Job[] jobs = {

                    new Job("String decode: csn") {
                    public void work() throws Throwable {
                        for (int i = 0; i < itrs; i++)
                            new String(bs, csn);
                    }},

                    new Job("String decode: cs") {
                    public void work() throws Throwable {
                        for (int i = 0; i < itrs; i++)
                            new String(bs, cs);
                    }},

                    new Job("String encode: csn") {
                    public void work() throws Throwable {
                        for (int i = 0; i < itrs; i++)
                            str.getBytes(csn);
                    }},

                    new Job("String encode: cs") {
                    public void work() throws Throwable {
                        for (int i = 0; i < itrs; i++)
                            str.getBytes(cs);
                    }},
                };
                sum.add(time(jobs));

            }
        }
    }
}
