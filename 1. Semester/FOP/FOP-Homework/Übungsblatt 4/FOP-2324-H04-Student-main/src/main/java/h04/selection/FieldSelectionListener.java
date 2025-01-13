package h04.selection;

import fopbot.Field;

/**
 * An Interface that can be used in a class that needs to execute commands on a selected field.
 */
public interface FieldSelectionListener {
    /**
     * Can be called using a selected field to execute commands with the field.
     * @param field that was selected.
     */
    public void onFieldSelection(Field field);
}
