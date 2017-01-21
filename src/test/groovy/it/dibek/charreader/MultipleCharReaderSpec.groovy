package it.dibek.charreader

import it.dibek.charreader.reader.CharacterReader
import it.dibek.charreader.reader.SimpleCharacterReader
import it.dibek.charreader.reader.SlowCharacterReader
import spock.lang.Specification

/**
 * Created by dibek on 21/01/17.
 */
class MultipleCharReaderSpec extends Specification {
    def "call multiple readers method"() {

        given:

            SlowCharacterReader[] characterReaders = new SlowCharacterReader[5]
            int countArrays = characterReaders.length
            characterReaders[--countArrays] = new SlowCharacterReader()
            while (countArrays){
                characterReaders[--countArrays] = new SlowCharacterReader()
            }
        when:
            MultipleCharReader multipleCharReader = new MultipleCharReader(characterReaders)
            WordCounter[][] wordCounters = multipleCharReader.runReaderAtFixedTime()
        then:
            wordCounters.length == characterReaders.length

           for (WordCounter[] wordContersArray: wordCounters) {
               wordContersArray[0].getCount() == 6
               wordContersArray[0].word.equals('the')
           }



    }
}
