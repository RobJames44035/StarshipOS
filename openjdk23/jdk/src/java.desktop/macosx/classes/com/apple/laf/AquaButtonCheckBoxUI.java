/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.apple.laf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

import apple.laf.JRSUIConstants.*;

import com.apple.laf.AquaUtilControlSize.*;
import com.apple.laf.AquaUtils.*;

public class AquaButtonCheckBoxUI extends AquaButtonLabeledUI {
    private static final RecyclableSingleton<AquaButtonCheckBoxUI> instance = new RecyclableSingletonFromDefaultConstructor<AquaButtonCheckBoxUI>(AquaButtonCheckBoxUI.class);
    private static final RecyclableSingleton<ImageIcon> sizingIcon = new RecyclableSingleton<ImageIcon>() {
        protected ImageIcon getInstance() {
            return new ImageIcon(AquaNativeResources.getRadioButtonSizerImage());
        }
    };

    public static ComponentUI createUI(final JComponent b) {
        return instance.get();
    }

    public static Icon getSizingCheckBoxIcon() {
        return sizingIcon.get();
    }

    public String getPropertyPrefix() {
        return "CheckBox" + ".";
    }

    protected AquaButtonBorder getPainter() {
        return new CheckBoxButtonBorder();
    }

    public static class CheckBoxButtonBorder extends LabeledButtonBorder {
        public CheckBoxButtonBorder() {
            super(new SizeDescriptor(new SizeVariant().replaceMargins("CheckBox.margin")));
            painter.state.set(Widget.BUTTON_CHECK_BOX);
        }

        public CheckBoxButtonBorder(final CheckBoxButtonBorder sizeDescriptor) {
            super(sizeDescriptor);
        }
    }
}
