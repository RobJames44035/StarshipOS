/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4124729
 * @summary Test that constrtructor LineBorder(?,?,?) is public
 * @author Andrey Pikalev
 */

import java.awt.Color;
import javax.swing.border.LineBorder;

public class Test4124729 {
    public static void main(String[] args) {
        new LineBorder(Color.BLUE, 3, true);
    }
}
