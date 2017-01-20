package it.dibek.charreader

import it.dibek.charreader.reader.CharacterReader
import it.dibek.charreader.reader.SimpleCharacterReader
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

    def 'return array with all words' (){
        given:
            CharacterReader characterReader = new SimpleCharacterReader()
            CharReader charReader = new CharReader(characterReader)
        when:
         String[] resultArray = charReader.populateArrayWords()

        then:
            resultArray[resultArray.length-1] != null


    }
}
