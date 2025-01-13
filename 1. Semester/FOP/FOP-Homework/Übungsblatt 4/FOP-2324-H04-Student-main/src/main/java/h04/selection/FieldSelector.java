package h04.selection;

/**
 * An interface that is used in classes that assign FieldSelectionListeners.
 */
public interface FieldSelector {
    /**
     * Assignes a given FieldSelectionListener to an object.
     * @param fieldSelectionListener is the listener that will be assigned.
     */
    public void setFieldSelectionListener(FieldSelectionListener fieldSelectionListener);
}
