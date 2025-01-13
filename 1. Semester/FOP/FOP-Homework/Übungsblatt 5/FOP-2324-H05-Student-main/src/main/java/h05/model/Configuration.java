package h05.model;

/**
 * The interface Configuration, which rates allows to implement an algorythm to rate the computer with.
 */
public interface Configuration {
    /**
     * Rate the computer with a given componentRater.
     *
     * @param componentRater the component rater
     */
    void rateBy(ComponentRater componentRater);
}
