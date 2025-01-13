package h04.selection;


import fopbot.*;

import java.awt.*;

/**
 * Is a class with which one can select a field using keyboard inputs.
 */
public class KeyboardFieldSelector implements FieldSelector, KeyPressListener {

    private FieldSelectionListener fieldSelectionListener;
    private boolean firstTimeUsed = true;
    private Field markedField;

    /**
     * Constructs a new KeyboardFieldSelector and
     * adds the Field selector to the global KeyPressListener
     */
    public KeyboardFieldSelector() {
        World.addKeyPressListener(this);
    }

    /**
     * Assigns a FieldSelectionListener to the FieldSelector
     * @param fieldSelectionListener the FieldSelectionListener that will be assigned
     */
    public void setFieldSelectionListener(FieldSelectionListener fieldSelectionListener) {
        this.fieldSelectionListener = fieldSelectionListener;
    }

    /**
     * Moves the field around the world with arrow keys.
     * Pressing Space selects the field.
     * @param event the key press event
     */
    public void onKeyPress(KeyPressEvent event) {

        Key key = event.getKey();
        KarelWorld world = event.getWorld();

        if(this.firstTimeUsed == true){
            this.markedField = world.getField(0,0);
            this.markedField.setFieldColor(Color.RED);
            this.firstTimeUsed = false;
        }


        if (key == Key.UP) {
            this.markedField.setFieldColor(null);
            this.markedField = world.getField(this.markedField.getX(), (this.markedField.getY() + 1) % World.getHeight());
            this.markedField.setFieldColor(Color.RED);
        }
        if (key == Key.DOWN) {
            this.markedField.setFieldColor(null);
            int y = this.markedField.getY() - 1;
            if(y<0) {
                this.markedField = world.getField(this.markedField.getX(), World.getHeight()-1);
            }
            else {
                this.markedField = world.getField(this.markedField.getX(), y);
            }
            this.markedField.setFieldColor(Color.RED);
        }
        if (key == Key.LEFT) {
            this.markedField.setFieldColor(null);
            int x = this.markedField.getX() - 1;
            if(x<0) {
                this.markedField = world.getField(World.getWidth()-1, this.markedField.getY());
            }
            else {
                this.markedField = world.getField(x, this.markedField.getY());
            }
            this.markedField.setFieldColor(Color.RED);
        }
        if (key == Key.RIGHT) {
            this.markedField.setFieldColor(null);
            this.markedField = world.getField((this.markedField.getX() + 1) % World.getWidth(), this.markedField.getY());
            this.markedField.setFieldColor(Color.RED);
        }

        if(key == Key.SPACE){
            if (this.markedField != null){
                fieldSelectionListener.onFieldSelection(this.markedField);
            }
        }

    }
}
