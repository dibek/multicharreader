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
            charReader.populateInfoMap()
        expect:

        charReader.getSize() > 0

    }
}
