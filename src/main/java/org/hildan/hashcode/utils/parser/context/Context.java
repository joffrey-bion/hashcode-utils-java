package org.hildan.hashcode.utils.parser.context;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.hildan.hashcode.utils.parser.InputParsingException;
import org.hildan.hashcode.utils.parser.config.Config;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the current parsing context. It provides methods to access the input data and the context variables.
 */
public class Context {

    private final Map<String, String> variables;

    private final LineNumberScanner scanner;

    /**
     * Creates a new parsing context using the given {@link Reader} to access the input.
     *
     * @param reader
     *         the reader to use to read the input
     * @param config
     *         the config defining how the parser should behave
     */
    public Context(Reader reader, Config config) {
        this.variables = new HashMap<>();
        this.scanner = new LineNumberScanner(reader, config.getSeparator());
    }

    /**
     * Scans the next token of the input as a string.
     *
     * @return the string scanned from the input
     *
     * @throws NoMoreLinesToReadException
     *         if there is no more lines to read
     * @throws InputParsingException
     *         if an error occurs while reading the input
     */
    @NotNull
    public String readString() throws NoMoreLinesToReadException {
        return scanner.nextString();
    }

    /**
     * Scans the next token of the input as an int.
     *
     * @return the int scanned from the input
     *
     * @throws NoMoreLinesToReadException
     *         if there is no more lines to read
     * @throws InputParsingException
     *         if the input could not be parsed as an int
     */
    public int readInt() throws InputParsingException {
        return scanner.nextInt();
    }

    /**
     * Reads and returns the next line of input as an array of string tokens.
     *
     * @return the next line of input
     *
     * @throws IncompleteLineReadException
     *         if the previous line was not completely consumed
     * @throws NoMoreLinesToReadException
     *         if there is no more lines to read
     * @throws InputParsingException
     *         if an error occurs while reading the input
     */
    public String[] readLine() throws InputParsingException {
        return scanner.nextLineTokens();
    }

    /**
     * Releases potential resources used by the reader. Should be called when parsing is over.
     *
     * @throws IncompleteInputReadException
     *         if there is still some input left to read
     */
    public void closeReader() {
        scanner.close();
    }

    /**
     * Wraps the given exception into an {@link InputParsingException}.
     *
     * @param ex
     *         the exception to wrap
     *
     * @return a new {@link InputParsingException} with the given exception as cause
     */
    public InputParsingException wrapException(Exception ex) {
        return new InputParsingException(scanner.getLineNumber(), scanner.getCurrentLine(), ex.getMessage(), ex);
    }

    /**
     * Gets the value of the given context variable.
     *
     * @param key
     *         the name of the context variable to access
     *
     * @return the value of the given context variable
     *
     * @throws UndefinedVariableException
     *         if no variable is found with the given name
     */
    public String getVariable(String key) throws UndefinedVariableException {
        String value = variables.get(key);
        if (value == null) {
            throw new UndefinedVariableException(key);
        }
        return value;
    }

    /**
     * Gets the value of the given context variable, converted into an int.
     *
     * @param key
     *         the name of the context variable to access
     *
     * @return the value of the given context variable
     *
     * @throws UndefinedVariableException
     *         if no variable is found with the given name
     * @throws InputParsingException
     *         if the variable value cannot be converted to an int
     */
    public int getVariableAsInt(String key) throws InputParsingException {
        String size = getVariable(key);
        try {
            return Integer.parseInt(size);
        } catch (NumberFormatException e) {
            throw new InputParsingException("Variable '" + key + "' cannot be converted into an int", e);
        }
    }

    /**
     * Sets the given variable to the given value.
     *
     * @param key
     *         the name of the context variable to create or update
     * @param value
     *         the value to set the variable to
     */
    public void setVariable(String key, String value) {
        variables.put(key, value);
    }
}
