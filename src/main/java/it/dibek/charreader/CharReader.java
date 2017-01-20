package it.dibek.charreader;

import it.dibek.charreader.exception.EndOfStreamException;
import it.dibek.charreader.reader.CharacterReader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by giuseppe.dibella on 18/01/2017.
 */
public class CharReader {


    private static final char WORD_SPACE_SEPARATOR = ' ';
    private static final char SYMBOL_COMMA = ',';
    private static final char SYMBOL_DOT = '.' ;
    private CharacterReader characterReader;

    private int size;

    private BinaryTree<WordCounter> binaryTree;

    private Map<String,Integer> mapWords;



    public CharReader(CharacterReader characterReader) {
        this.characterReader = characterReader;
        this.binaryTree = new BinaryTree<>();
        this.mapWords = new ConcurrentHashMap<>();
    }


    WordCounter[] populateArrayWords(){


        StringBuilder stringBuilder = new StringBuilder();
        AtomicInteger countWord = new AtomicInteger();
        try {
            char nextChar = characterReader.getNextChar();
          while(true) {
              while (nextChar != WORD_SPACE_SEPARATOR && nextChar != SYMBOL_COMMA && nextChar != SYMBOL_DOT) {
                  stringBuilder.append(nextChar);
                  nextChar = characterReader.getNextChar();
              }
              if (stringBuilder.length() > 0) {
                  String word = stringBuilder.toString().trim();

                  if (mapWords.containsKey(word)) {
                      Integer count = mapWords.get(word) + 1;
                      mapWords.replace(word,count);

                  }
                  else {
                      mapWords.put(word,1);
                  }

                  countWord.incrementAndGet();
              }
              stringBuilder = new StringBuilder();
              nextChar = characterReader.getNextChar();
          }
        }
        catch (EndOfStreamException ex) {

        }
        for (Map.Entry<String,Integer> entry: mapWords.entrySet()) {
            WordCounter wordCounter   = new WordCounter(entry.getKey(),entry.getValue());
            binaryTree.addObject(wordCounter);
        }
        WordCounter[] sortArray = new WordCounter[mapWords.keySet().size()];
        Node nodeToVisit = binaryTree.findNode(-1);
        binaryTree.getIndexSorted(nodeToVisit, sortArray,sortArray.length-1,"");
        this.size = sortArray.length;
        return sortArray;
    }

    int getSize(){
        return this.size;
    }


    private class WordCounter implements Comparable<WordCounter>{

        private String word;
        private int count;


        public WordCounter(String word, int count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public int compareTo(WordCounter o) {
            return this.word.compareTo(o.word);
        }

        @Override
        public String toString() {
            return "WordCounter{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
