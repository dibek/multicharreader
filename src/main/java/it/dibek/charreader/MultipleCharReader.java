package it.dibek.charreader;

import it.dibek.charreader.reader.CharacterReader;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dibek on 21/01/17.
 */
public class MultipleCharReader {

    private static final char WORD_SPACE_SEPARATOR = ' ';
    private static final char SYMBOL_COMMA = ',';
    private static final char SYMBOL_DOT = '.';
    private CharacterReader[] characterReaders;

    public MultipleCharReader(CharacterReader[] characterReaders) {
        this.characterReaders = characterReaders;
    }


    /**
     * Extract from the multiple streams the array of WordCounter info with a delay of 10s between each execution
     * @return
     */
    public WordCounter[][] runReaderAtFixedTime() {
        ExecutorService executor = Executors.newFixedThreadPool(characterReaders.length);
        WordCounter[][] combinedWordCounter = new WordCounter[characterReaders.length][];
        final int countReaders = characterReaders.length;
        CountDownLatch latch = new CountDownLatch(characterReaders.length);
        AtomicInteger arrayIndexAtomic = new AtomicInteger(characterReaders.length);
        for (CharacterReader characterReader : characterReaders) {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("Start thread ");
                executor.submit(() -> {
                    CharReader charReader = new CharReader(characterReader);
                    combinedWordCounter[arrayIndexAtomic.decrementAndGet()] = charReader.populateArrayWordsWithLatch(latch);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            if (latch.getCount() == 1){
                latch.countDown();
            }

            latch.await();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        return combinedWordCounter;
    }

}