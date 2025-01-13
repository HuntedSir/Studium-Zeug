package h04.selection;

import fopbot.Field;
import fopbot.FieldClickEvent;
import fopbot.FieldClickListener;
import fopbot.World;

/**
 * Is a class with which one can select a field using double click with the mouse.
 */
public class MouseFieldSelector implements FieldSelector, FieldClickListener {
    private FieldSelectionListener fieldSelectionListener;
    private Field field;
    private boolean firstTimeBeingCalled = true;
    /**
     * Constructs a new MouseFieldSelector and
     * adds the Field selector to the global FieldClickListener
     */
    public MouseFieldSelector() {
        World.addFieldClickListener(this);
    }

    /**
     * Gets the field that was clicked with a mouse from the field click event
     * Executes onFieldSelection if the field was clicked a second time in a row.
     * @param event the field click event
     */
    public void onFieldClick(FieldClickEvent event) {
        Field clickedField = event.getField();
        if (!this.firstTimeBeingCalled) {
            if(this.field == clickedField){
                fieldSelectionListener.onFieldSelection(clickedField);
            }
        }
        this.firstTimeBeingCalled = false;
        this.field=clickedField;
    }

    /**
     * Assigns a FieldSelectionListener to the MouseFieldSelector.
     * @param fieldSelectionListener is the FieldSelectionListener that will be assigned.
     */
    public void setFieldSelectionListener(FieldSelectionListener fieldSelectionListener) {
        this.fieldSelectionListener = fieldSelectionListener;
    }
}
