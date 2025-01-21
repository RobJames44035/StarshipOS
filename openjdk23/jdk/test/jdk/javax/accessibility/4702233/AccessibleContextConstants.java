/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @summary Constant for testing public fields in AccessibleContext.
 */

public interface AccessibleContextConstants {

    String CLASS_NAME = "javax.accessibility.AccessibleContext";

    /**
     * Public fields values in AccessibleContext class.
     */
    String[][] FIELDS = new String[][] {
        { "ACCESSIBLE_NAME_PROPERTY", "AccessibleName" },
        { "ACCESSIBLE_DESCRIPTION_PROPERTY", "AccessibleDescription" },
        { "ACCESSIBLE_STATE_PROPERTY", "AccessibleState" },
        { "ACCESSIBLE_VALUE_PROPERTY", "AccessibleValue" },
        { "ACCESSIBLE_SELECTION_PROPERTY", "AccessibleSelection" },
        { "ACCESSIBLE_CARET_PROPERTY", "AccessibleCaret" },
        { "ACCESSIBLE_VISIBLE_DATA_PROPERTY", "AccessibleVisibleData" },
        { "ACCESSIBLE_CHILD_PROPERTY", "AccessibleChild" },
        { "ACCESSIBLE_ACTIVE_DESCENDANT_PROPERTY",
        "AccessibleActiveDescendant" },
        { "ACCESSIBLE_TABLE_CAPTION_CHANGED", "accessibleTableCaptionChanged" },
        { "ACCESSIBLE_TABLE_SUMMARY_CHANGED", "accessibleTableSummaryChanged" },
        { "ACCESSIBLE_TABLE_MODEL_CHANGED", "accessibleTableModelChanged" },
        { "ACCESSIBLE_TABLE_ROW_HEADER_CHANGED",
        "accessibleTableRowHeaderChanged" },
        { "ACCESSIBLE_TABLE_ROW_DESCRIPTION_CHANGED",
        "accessibleTableRowDescriptionChanged" },
        { "ACCESSIBLE_TABLE_COLUMN_HEADER_CHANGED",
        "accessibleTableColumnHeaderChanged" },
        { "ACCESSIBLE_TABLE_COLUMN_DESCRIPTION_CHANGED",
        "accessibleTableColumnDescriptionChanged" },
        { "ACCESSIBLE_ACTION_PROPERTY", "accessibleActionProperty" },
        { "ACCESSIBLE_HYPERTEXT_OFFSET", "AccessibleHypertextOffset" },
        { "ACCESSIBLE_TEXT_PROPERTY", "AccessibleText" },
        { "ACCESSIBLE_INVALIDATE_CHILDREN", "accessibleInvalidateChildren" },
        { "ACCESSIBLE_TEXT_ATTRIBUTES_CHANGED",
        "accessibleTextAttributesChanged" },
        { "ACCESSIBLE_COMPONENT_BOUNDS_CHANGED",
        "accessibleComponentBoundsChanged" } };

        /**
         * Old(removed) fields in AccessibleContext class.
         */
        String[] OLD_FIELDS = new String[] {};
}

