package it.dibek.charreader.reader;

/**
 * @author mattf - 2013-05-15
 */
public interface CharacterReader {

    /**
     * @return the next character in the stream
     * @throws it.dibek.charreader.exception.EndOfStreamException if there are no more characters
     */
    char getNextChar();
}
