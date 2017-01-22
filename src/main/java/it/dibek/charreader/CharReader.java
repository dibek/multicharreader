package it.dibek.charreader;

import it.dibek.charreader.exception.EndOfStreamException;
import it.dibek.charreader.reader.CharacterReader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by giuseppe.dibella on 18/01/2017.
 */
public class CharReader {


    private CharacterReader characterReader;

    private int size;

    private BinaryTree<WordCounter> binaryTree;

    private Map<String, Integer> mapWords;


    /**
     * A simple class to estract from a stream words and ordering them with a basic binarytree
     *
     * @param characterReader
     */
    public CharReader(CharacterReader characterReader) {
        this.characterReader = characterReader;
        this.binaryTree = new BinaryTree<>();
        this.mapWords = new ConcurrentHashMap<>();
    }

    /**
     * A wrapper to execute the extraction in a multithreading environment
     *
     * @param latch
     * @return
     */
    void populateArrayWordsWithLatch(WordCounter[][] combinedWordCounter, AtomicInteger arrayIndexAtomic, CountDownLatch latch) {
        WordCounter[] wordCounters = this.populateArrayWords();
        combinedWordCounter[arrayIndexAtomic.decrementAndGet()] = wordCounters;
        System.out.println("latch counter before countdown: " + latch.getCount());
        latch.countDown();
    }

    /**
     * A method to extract the words from the stream and put them in a sorted array using a binarytree
     *
     * @return
     */
    WordCounter[] populateArrayWords() {
        StringBuffer stringBuffer = new StringBuffer();
        AtomicInteger countWord = new AtomicInteger();
        try {
            char nextChar = characterReader.getNextChar();
            while (true) {
                while (Character.isAlphabetic(nextChar)) {
                    stringBuffer.append(nextChar);
                    nextChar = characterReader.getNextChar();
                }
                if (stringBuffer.length() > 0) {
                    String word = stringBuffer.toString().trim();
                    countOccurenceWord(word);
                    countWord.incrementAndGet();
                }
                stringBuffer = new StringBuffer();
                nextChar = characterReader.getNextChar();
            }
        } catch (EndOfStreamException ex) {

        }
        for (Map.Entry<String, Integer> entry : mapWords.entrySet()) {
            WordCounter wordCounter = new WordCounter(entry.getKey(), entry.getValue());
            binaryTree.addObject(wordCounter);
        }
        WordCounter[] sortArray = new WordCounter[mapWords.keySet().size()];
        Node nodeToVisit = binaryTree.findNode(-1);
        binaryTree.collectSortedAlphaNumeric(nodeToVisit, sortArray, sortArray.length - 1, "");
        this.size = sortArray.length;
        for (WordCounter wordCounter : sortArray) {
            System.out.println(wordCounter);
        }
        return sortArray;
    }

    private void countOccurenceWord(String word) {
        if (mapWords.containsKey(word)) {
            Integer count = mapWords.get(word) + 1;
            mapWords.replace(word, count);
        } else {
            mapWords.put(word, 1);
        }
    }

    int getSize() {
        return this.size;
    }


}

