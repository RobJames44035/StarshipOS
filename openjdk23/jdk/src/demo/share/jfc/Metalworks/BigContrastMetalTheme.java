/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */



import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;


/**
 * This class describes a theme using "green" colors.
 *
 * @author Steve Wilson
 * @author Alexander Kouznetsov
 */
public class BigContrastMetalTheme extends ContrastMetalTheme {

    @Override
    public String getName() {
        return "Low Vision";
    }
    private final FontUIResource controlFont = new FontUIResource("Dialog",
            Font.BOLD, 24);
    private final FontUIResource systemFont = new FontUIResource("Dialog",
            Font.PLAIN, 24);
    private final FontUIResource windowTitleFont = new FontUIResource("Dialog",
            Font.BOLD, 24);
    private final FontUIResource userFont = new FontUIResource("SansSerif",
            Font.PLAIN, 24);
    private final FontUIResource smallFont = new FontUIResource("Dialog",
            Font.PLAIN, 20);

    @Override
    public FontUIResource getControlTextFont() {
        return controlFont;
    }

    @Override
    public FontUIResource getSystemTextFont() {
        return systemFont;
    }

    @Override
    public FontUIResource getUserTextFont() {
        return userFont;
    }

    @Override
    public FontUIResource getMenuTextFont() {
        return controlFont;
    }

    @Override
    public FontUIResource getWindowTitleFont() {
        return windowTitleFont;
    }

    @Override
    public FontUIResource getSubTextFont() {
        return smallFont;
    }

    @Override
    public void addCustomEntriesToTable(UIDefaults table) {
        super.addCustomEntriesToTable(table);

        final int internalFrameIconSize = 30;
        table.put("InternalFrame.closeIcon", MetalIconFactory.
                getInternalFrameCloseIcon(internalFrameIconSize));
        table.put("InternalFrame.maximizeIcon", MetalIconFactory.
                getInternalFrameMaximizeIcon(internalFrameIconSize));
        table.put("InternalFrame.iconifyIcon", MetalIconFactory.
                getInternalFrameMinimizeIcon(internalFrameIconSize));
        table.put("InternalFrame.minimizeIcon", MetalIconFactory.
                getInternalFrameAltMaximizeIcon(internalFrameIconSize));


        Border blackLineBorder = new BorderUIResource(new MatteBorder(2, 2, 2, 2,
                Color.black));
        Border textBorder = blackLineBorder;

        table.put("ToolTip.border", blackLineBorder);
        table.put("TitledBorder.border", blackLineBorder);


        table.put("TextField.border", textBorder);
        table.put("PasswordField.border", textBorder);
        table.put("TextArea.border", textBorder);
        table.put("TextPane.font", controlFont);

        table.put("ScrollPane.border", blackLineBorder);

        table.put("ScrollBar.width", 25);



    }
}
