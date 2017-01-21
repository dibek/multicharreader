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
                    charReader.populateArrayWordsWithLatch(combinedWordCounter, arrayIndexAtomic,latch);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            latch.await();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        return combinedWordCounter;
    }

}
