/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package sun.print;

import java.io.Serial;
import java.util.ArrayList;

import javax.print.attribute.EnumSyntax;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.OutputBin;

public final class CustomOutputBin extends OutputBin {
    private static ArrayList<String> customStringTable = new ArrayList<>();
    private static ArrayList<CustomOutputBin> customEnumTable = new ArrayList<>();
    private String choiceName;

    private CustomOutputBin(int x) {
        super(x);
    }

    private static synchronized int nextValue(String name) {
      customStringTable.add(name);
      return (customStringTable.size()-1);
    }

    private CustomOutputBin(String name, String choice) {
        super(nextValue(name));
        choiceName = choice;
        customEnumTable.add(this);
    }

    /**
     * Creates a custom output bin
     */
    public static synchronized CustomOutputBin createOutputBin(String name, String choice) {
        for (CustomOutputBin bin : customEnumTable) {
            if (bin.getChoiceName().equals(choice) && bin.getCustomName().equals(name)) {
                return bin;
            }
        }
        return new CustomOutputBin(name, choice);
    }

    private static final long serialVersionUID = 3018751086294120717L;

    /**
     * Returns the command string for this media tray.
     */
    public String getChoiceName() {
        return choiceName;
    }

    /**
     * Returns the string table for super class MediaTray.
     */
    public OutputBin[] getSuperEnumTable() {
      return (OutputBin[])super.getEnumValueTable();
    }

    /**
     * Returns the string table for class CustomOutputBin.
     */
    @Override
    protected String[] getStringTable() {
      String[] nameTable = new String[customStringTable.size()];
      return customStringTable.toArray(nameTable);
    }

    /**
     * Returns a custom bin name
     */
    public String getCustomName() {
        return customStringTable.get(getValue() - getOffset());
    }

    /**
     * Returns the enumeration value table for class CustomOutputBin.
     */
    @Override
    protected CustomOutputBin[] getEnumValueTable() {
        CustomOutputBin[] enumTable = new CustomOutputBin[customEnumTable.size()];
      return customEnumTable.toArray(enumTable);
    }
}
