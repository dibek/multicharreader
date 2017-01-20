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
    private CharacterReader characterReader;

    private int size;

    private BinaryTree<String> binaryTree;

    private Map<String,Integer> mapWords;



    public CharReader(CharacterReader characterReader) {
        this.characterReader = characterReader;
        this.binaryTree = new BinaryTree<>();
        this.mapWords = new ConcurrentHashMap<>();
    }


    String[] populateArrayWords(){


        StringBuilder stringBuilder = new StringBuilder();
        AtomicInteger countWord = new AtomicInteger();
        try {
            char nextChar = characterReader.getNextChar();
          while(true) {
              while (nextChar != WORD_SPACE_SEPARATOR) {
                  stringBuilder.append(nextChar);
                  nextChar = characterReader.getNextChar();
              }
              if (stringBuilder.length() > 0) {
                  String word = stringBuilder.toString();
                  binaryTree.addObject(word);
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
        String[] sortArray = new String[mapWords.keySet().size()];
        Node nodeToVisit = binaryTree.findNode(-1);
        binaryTree.getIndexReverseSorted(nodeToVisit, sortArray,sortArray.length-1,"");
        this.size = sortArray.length;
        return sortArray;
    }

    int getSize(){
        return this.size;
    }


}
