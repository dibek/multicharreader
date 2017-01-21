package it.dibek.charreader;

/**
 * Created by dibek on 21/01/17.
 */
class WordCounter implements Comparable<WordCounter>{



    private String word;
    private Integer count;

    public String getWord() {
        return word;
    }

    public Integer getCount() {
        return count;
    }

    public WordCounter(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    @Override
    public int compareTo(WordCounter o) {
        int i = o.count.compareTo(count);
        if (i != 0) return i;
        return word.compareTo(o.word);
    }

    @Override
    public String toString() {
        return "WordCounter{" +
                "word='" + word + '\'' +
                ", count=" + count +
                '}';
    }
}
