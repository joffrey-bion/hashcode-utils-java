package org.hildan.hashcode.utils.parser.context;

import org.hildan.hashcode.utils.parser.InputParsingException;

/**
 * Thrown if the parsing ends while there is still input to read.
 */
public class IncompleteReadException extends InputParsingException {

    public IncompleteReadException(int nbLinesLeft) {
        super(String.format("The end of the input was not consumed, %s lines remaining", nbLinesLeft));
    }
}
