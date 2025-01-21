/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.text.View;
import javax.swing.text.html.CSS;

/*
 * @test
 * @bug 8326734
 * @summary Tests different combinations of setting 'line-through'
 * @run main HTMLStrikeOnly
 */
public class HTMLStrikeOnly {
    private static final String HTML = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>line-through</title>
                <style>
                    .lineThrough   { text-decoration: line-through }
                </style>
            </head>
            <body>
            <p><s><span style='text-decoration: line-through'>line-through?</span></s></p>
            <p><strike><span style='text-decoration: line-through'>line-through?</span></strike></p>
            <p><span style='text-decoration: line-through'><s>line-through?</s></span></p>
            <p><span style='text-decoration: line-through'><strike>line-through?</strike></span></p>

            <p><s><span class="lineThrough">line-through?</span></s></p>
            <p><strike><span class="lineThrough">line-through?</span></strike></p>
            <p><span class="lineThrough"><s>line-through?</s></span></p>
            <p><span class="lineThrough"><strike>line-through?</strike></span></p>

            <p style='text-decoration: line-through'><s>line-through?</s></p>
            <p style='text-decoration: line-through'><strike>line-through?</strike></p>
            <p style='text-decoration: line-through'><span style='text-decoration: line-through'>line-through?</span></p>

            <p class="lineThrough"><s>line-through</s></p>
            <p class="lineThrough"><strike>line-through</strike></p>
            <p class="lineThrough"><span style='text-decoration: line-through'>line-through</span></p>
            <p class="lineThrough"><span class="lineThrough">line-through</span></p>
            </body>
            </html>
            """;
    public static void main(String[] args) {
        final JEditorPane html = new JEditorPane("text/html", HTML);
        html.setEditable(false);

        final Dimension size = html.getPreferredSize();
        html.setSize(size);

        BufferedImage image = new BufferedImage(size.width, size.height,
                                                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        // Paint the editor pane to ensure all views are created
        html.paint(g);
        g.dispose();

        int errorCount = 0;
        String firstError = null;

        System.out.println("----- Views -----");
        final View bodyView = html.getUI()
                                  .getRootView(html)
                                  .getView(1)
                                  .getView(1);
        for (int i = 0; i < bodyView.getViewCount(); i++) {
            View pView = bodyView.getView(i);
            View contentView = getContentView(pView);

            String decoration =
                    contentView.getAttributes()
                               .getAttribute(CSS.Attribute.TEXT_DECORATION)
                               .toString();

            System.out.println(i + ": " + decoration);
            if (!decoration.contains("line-through")
                || decoration.contains("underline")) {
                errorCount++;
                if (firstError == null) {
                    firstError = "Line " + i + ": " + decoration;
                }
            }
        }

        if (errorCount > 0) {
            saveImage(image);
            throw new RuntimeException(errorCount + " error(s) found, "
                                       + "the first one: " + firstError);
        }
    }

    private static View getContentView(View parent) {
        View view = parent.getView(0);
        return view.getViewCount() > 0
               ? getContentView(view)
               : view;
    }

    private static void saveImage(BufferedImage image) {
        try {
            ImageIO.write(image, "png",
                          new File("html.png"));
        } catch (IOException ignored) { }
    }
}
