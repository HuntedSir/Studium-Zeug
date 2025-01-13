package h12.template.errors;

import h12.template.fsm.HeaderParameter;

/**
 * Exception, if Parameter is already specified
 */
public class ParameterAlreadySpecifiedException extends KissParserException{

    /**
     * Constructs a new {@link ParameterAlreadySpecifiedException}
     * @param parameter The Parameter which is already specified
     */
    public ParameterAlreadySpecifiedException(final HeaderParameter parameter) {
        super("Header already specified: %s".formatted(parameter));
    }
}
