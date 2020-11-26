import java.util.ArrayList;

public class Twitter {

    MyHashTable<String, Tweet> authorMessageTable;
    MyHashTable<String, ArrayList<Tweet>> dateMessageTable;
    ArrayList<String> stopWords;
    MyHashTable<String, Integer> wordCounts;

    
    public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
        int bucketCount = (int) (tweets.size() / 0.75) + 1;
        authorMessageTable = new MyHashTable<String, Tweet>(bucketCount);
        dateMessageTable = new MyHashTable<String, ArrayList<Tweet>>(bucketCount);
        wordCounts = new MyHashTable<String, Integer>(7);

        this.stopWords = stopWords;

        for (Tweet t : tweets)
            addTweet(t);
    }

    /**
     * Add Tweet t to this Twitter O(1)
     */
    public void addTweet(Tweet t) {
        authorMessageTable.put(t.getAuthor(), t);

        String date = t.getDateAndTime().split(" ")[0];
        ArrayList<Tweet> tweetsOnDate = dateMessageTable.get(date);
        if (tweetsOnDate == null)
            tweetsOnDate = new ArrayList<Tweet>();
        tweetsOnDate.add(t);
        dateMessageTable.put(date, tweetsOnDate);

        for (String word : getWords(t.getMessage())) {
            word = word.toLowerCase();
            if (!stopWords.contains(word)) {
                Integer current = wordCounts.get(word);
                if (current == null)
                    current = 0;
                current++;
                wordCounts.put(word, current);
            }
        }
    }

    /**
     * Search this Twitter for the latest Tweet of a given author. If there are no
     * tweets from the given author, then the method returns null. O(1)
     */
    public Tweet latestTweetByAuthor(String author) {
        return authorMessageTable.get(author);
    }

    /**
     * Search this Twitter for Tweets by `date' and return an ArrayList of all such
     * Tweets. If there are no tweets on the given date, then the method returns
     * null. O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        return dateMessageTable.get(date);
    }

    /**
     * Returns an ArrayList of words (that are not stop words!) that appear in the
     * tweets. The words should be ordered from most frequent to least frequent by
     * counting in how many tweet messages the words appear. Note that if a word
     * appears more than once in the same tweet, it should be counted only once.
     */
    public ArrayList<String> trendingTopics() {
        return MyHashTable.fastSort(wordCounts);
    }

    /**
     * An helper method you can use to obtain an ArrayList of words from a String,
     * separating them based on apostrophes and space characters. All character that
     * are not letters from the English alphabet are ignored.
     */
    private static ArrayList<String> getWords(String msg) {
        msg = msg.replace('\'', ' ');
        String[] words = msg.split(" ");
        ArrayList<String> wordsList = new ArrayList<String>(words.length);
        for (int i = 0; i < words.length; i++) {
            String w = "";
            for (int j = 0; j < words[i].length(); j++) {
                char c = words[i].charAt(j);
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
                    w += c;
            }
            wordsList.add(w);
        }
        return wordsList;
    }

}
