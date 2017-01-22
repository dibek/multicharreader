package it.dibek.charreader.reader;

import it.dibek.charreader.exception.EndOfStreamException;

import java.util.regex.Pattern;

/**
 * Created by dibek on 22/01/17.
 */
public class PromptCharacterReader implements CharacterReader {

    private String promptContent;

    private int position = 0;

    private final static Pattern LTRIM = Pattern.compile("^\\s+");



    public PromptCharacterReader(String promptContent) {
        this.promptContent = ltrim(promptContent);
    }

    @Override
    public char getNextChar() {
        if(position >= promptContent.length())
            throw new EndOfStreamException();

        return promptContent.charAt(position++);
    }

    public static String ltrim(String s) {
        return LTRIM.matcher(s).replaceAll("");
    }
}
