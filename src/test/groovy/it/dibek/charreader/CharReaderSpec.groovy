package it.dibek.charreader

import it.dibek.charreader.reader.CharacterReader
import it.dibek.charreader.reader.SimpleCharacterReader
import it.dibek.charreader.reader.SlowCharacterReader
import javafx.collections.transformation.SortedList
import spock.lang.Specification

/**
 * Created by giuseppe.dibella on 18/01/2017.
 */

class CharReaderSpec extends Specification {



    def 'word counter and frequency is zezo'() {
        given:
            CharacterReader characterReader = new SimpleCharacterReader()
            CharReader charReader = new CharReader(characterReader)
        expect:

            charReader.getSize() == 0
    }

    def 'when populate map size is bigger than zero'(){
        given:
            CharacterReader characterReader = new SimpleCharacterReader()
            CharReader charReader = new CharReader(characterReader)
            charReader.populateArrayWords()
        expect:

            charReader.getSize() > 0

    }

    def 'return array with all words and counters' (){
        given:
            CharacterReader characterReader = new SimpleCharacterReader()
            CharReader charReader = new CharReader(characterReader)
        when:
         WordCounter[] resultArray = charReader.populateArrayWords()

        then:
            resultArray.length == 88
            resultArray[resultArray.length-1] != null
            resultArray[0] != null

    }

    def 'return array with all words and counters with reverse counter sorting' (){
        given:
            CharacterReader characterReader = new SimpleCharacterReader()
            CharReader charReader = new CharReader(characterReader)
        when:
            WordCounter[] resultArray = charReader.populateArrayWords()
        then:
            resultArray[0] != null
            resultArray[0].getCount() == 18
            resultArray[0].word.equals('the')
    }


    def 'call twice the populateArrayWords do not change the result'() {
        given:
            CharacterReader characterReader = new SimpleCharacterReader()
            CharReader charReader = new CharReader(characterReader)
        when:
            WordCounter[] resultArray = charReader.populateArrayWords()
            WordCounter[] resultArray2 = charReader.populateArrayWords()
        then:
            resultArray[0] != null
            resultArray[0].getCount() == 18
            resultArray[0].word.equals('the')
            resultArray2[0] != null
            resultArray2[0].getCount() == 18
            resultArray2[0].word.equals('the')
    }

}
