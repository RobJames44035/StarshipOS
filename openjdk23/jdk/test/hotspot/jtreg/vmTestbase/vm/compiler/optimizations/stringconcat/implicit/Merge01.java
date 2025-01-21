/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package vm.compiler.optimizations.stringconcat.implicit;

import nsk.share.StringGoldChecker;
import vm.compiler.share.CompilerTest;
import vm.compiler.share.CompilerTestLauncher;
import vm.compiler.share.Random;

public class Merge01 {

    private static final String GOLDEN_HASH = "1112689470";

    private static String staticS = "";
    private static Random random = new Random(11);

    private static String crop(String s) {
        int max = 100;
        if (s.length() > max) {
            return s.substring(5, 50);
        }
        return s;
    }

    private static String randomCut(String s, int size) {
        String res = new String();
        for (int i = 0; i < size; i++) {
            int r = random.nextInt(s.length());
            res += s.charAt(r);
        }
        return res;
    }


    public static void main(String[] args) {
        StringGoldChecker goldChecker = new StringGoldChecker(GOLDEN_HASH);
        goldChecker.print(CompilerTestLauncher.launch(test));
        goldChecker.check();
    }

    public static String randomString() {
        int t = random.nextInt(100);
        String pre = "" + t / 10;
        String post = "" + t + 'a'  + '\u307E' + random.nextInt(20);
        //merge:
        String s = crop(pre + post);
        staticS = crop(s + staticS);

        int r = pre.hashCode() * post.hashCode() - random.nextInt(1000);
        return randomCut(pre + "" + r % 50 + "-" + r + "o" + "\u306B" + post + staticS, 10);
    }

    private static final CompilerTest<Integer> test = new CompilerTest<Integer>("Merge01") {
        @Override
        public Integer execute(Random random) {
            String res = randomString() + "t\u306B";
            int t = 200;
            while (t > 0) {
                t -= random.nextInt(50);
                int i = res.hashCode() % 100;
                if (t > i) {
                    res += i + randomString();
                } else {
                    res += randomString();
                }
                res = crop(res);
            }

            return (res).hashCode();
        }
    };
}
