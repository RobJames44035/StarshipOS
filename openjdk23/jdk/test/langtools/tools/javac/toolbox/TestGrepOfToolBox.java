/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8241187
 * @summary ToolBox::grep should allow for negative filtering
 * @library /tools/lib
 * @build toolbox.ToolBox
 * @run main TestGrepOfToolBox
 */

import java.util.Arrays;
import java.util.List;

import toolbox.ToolBox;

public class TestGrepOfToolBox {
    public static void main(String[] args) {
        ToolBox tb = new ToolBox();
        List<String> input = Arrays.asList("apple", "banana", "cat", "dog", "end", "ending");

        String regex1 = ".*ana.*";
        List<String> expected1 = Arrays.asList("apple", "cat", "dog", "end", "ending");
        List<String> output1 = tb.grep(regex1, input, false);
        tb.checkEqual(expected1, output1);

        String regex2 = ".*nd.*";
        List<String> expected2 = Arrays.asList("apple", "banana", "cat", "dog");
        List<String> output2 = tb.grep(regex2, input, false);
        tb.checkEqual(expected2, output2);

        String regex3 = "apple";
        List<String> expected3 = Arrays.asList("banana", "cat", "dog", "end", "ending");
        List<String> output3 = tb.grep(regex3, input, false);
        tb.checkEqual(expected3, output3);

        String regex4 = ".*ana.*";
        List<String> expected4 = Arrays.asList("banana");
        List<String> output4 = tb.grep(regex4, input, true);
        tb.checkEqual(expected4, output4);

        String regex5 = ".*nd.*";
        List<String> expected5 = Arrays.asList("end", "ending");
        List<String> output5 = tb.grep(regex5, input, true);
        tb.checkEqual(expected5, output5);

        String regex6 = "apple";
        List<String> expected6 = Arrays.asList("apple");
        List<String> output6 = tb.grep(regex6, input, true);
        tb.checkEqual(expected6, output6);
    }
}
