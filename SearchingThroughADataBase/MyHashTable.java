import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K, V> implements Iterable<HashPair<K, V>> {
    // num of entries to the table
    private int numEntries;
    // num of buckets
    private int numBuckets;
    // load factor needed to check for rehashing
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K, V>>> buckets;

    // constructor
    public MyHashTable(int initialCapacity) {
        numBuckets = initialCapacity;
        numEntries = 0;
        buckets = new ArrayList<LinkedList<HashPair<K, V>>>();

        // We never want a null bucket so initialize all of them
        for (int i = 0; i < numBuckets; i++)
            buckets.add(new LinkedList<HashPair<K, V>>());
    }

    public int size() {
        return this.numEntries;
    }

    public boolean isEmpty() {
        return this.numEntries == 0;
    }

    public int numBuckets() {
        return this.numBuckets;
    }

    /**
     * Returns the buckets variable. Useful for testing purposes.
     */
    public ArrayList<LinkedList<HashPair<K, V>>> getBuckets() {
        return this.buckets;
    }

    /**
     * Given a key, return the bucket position for the key.
     */
    public int hashFunction(K key) {
        return Math.abs(key.hashCode()) % this.numBuckets;
    }

    /**
     * Takes a key and a value as input and adds the corresponding HashPair to this
     * HashTable. Expected average run time O(1)
     */
    public V put(K key, V value) {
        int bucketID = hashFunction(key);
        LinkedList<HashPair<K, V>> bucket = buckets.get(bucketID);

        // Check if the key already exists
        HashPair<K, V> match = null;
        for (HashPair<K, V> current : bucket) {
            if (current.getKey().equals(key)) {
                match = current;
                break;
            }
        }

        if (match == null) {
            // We have to add a new pair. Verify table size is ok
            if (1.0 * (numEntries + 1) / numBuckets >= MAX_LOAD_FACTOR) {
                // Resize table and ensure we have the correct bucket
                rehash();
                bucketID = hashFunction(key);
                bucket = buckets.get(bucketID);
            }
            numEntries++;

            HashPair<K, V> newPair = new HashPair<K, V>(key, value);
            bucket.add(newPair);

            // No old value to return
            return null;
        } else {
            // The key already exists
            V old = match.getValue();
            match.setValue(value);
            return old;
        }
    }

    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */

    public V get(K key) {
        int bucketID = hashFunction(key);
        LinkedList<HashPair<K, V>> bucket = buckets.get(bucketID);
        for (HashPair<K, V> current : bucket)
            if (current.getKey().equals(key))
                return current.getValue();
        return null;
    }

    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1)
     */
    public V remove(K key) {
        int bucketID = hashFunction(key);
        LinkedList<HashPair<K, V>> bucket = buckets.get(bucketID);

        for (HashPair<K, V> current : bucket) {
            if (current.getKey().equals(key)) {
                V value = current.getValue();
                bucket.remove(current);
                numEntries--;
                return value;
            }
        }

        return null;
    }

    /**
     * Method to double the size of the hashtable if load factor increases beyond
     * MAX_LOAD_FACTOR. Made public for ease of testing. Expected average runtime is
     * O(m), where m is the number of buckets
     */
    public void rehash() {
        ArrayList<LinkedList<HashPair<K, V>>> old = buckets;
        buckets = new ArrayList<LinkedList<HashPair<K, V>>>();

        numBuckets *= 2;
        for (int i = 0; i < numBuckets; i++)
            buckets.add(new LinkedList<HashPair<K, V>>());

        numEntries = 0;
        for (LinkedList<HashPair<K, V>> bucket : old)
            for (HashPair<K, V> pair : bucket)
                put(pair.getKey(), pair.getValue());
    }

    /**
     * Return a list of all the keys present in this hashtable. Expected average
     * runtime is O(m), where m is the number of buckets
     */

    public ArrayList<K> keys() {
        ArrayList<K> ret = new ArrayList<K>();
        for (HashPair<K, V> current : this)
            ret.add(current.getKey());
        return ret;
    }

    /**
     * Returns an ArrayList of unique values present in this hashtable. Expected
     * average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        MyHashTable<V, V> map = new MyHashTable<V, V>(numBuckets);
        for (HashPair<K, V> current : this)
            map.put(current.getValue(), current.getValue());
        return map.keys();
    }

    /**
     * This method takes as input an object of type MyHashTable with values that are
     * Comparable. It returns an ArrayList containing all the keys from the map,
     * ordered in descending order based on the values they mapped to.
     * 
     * The time complexity for this method is O(n^2), where n is the number of pairs
     * in the map.
     */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort(MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
            V element = entry.getValue();
            K toAdd = entry.getKey();
            int i = sortedResults.size() - 1;
            V toCompare = null;
            while (i >= 0) {
                toCompare = results.get(sortedResults.get(i));
                if (element.compareTo(toCompare) <= 0)
                    break;
                i--;
            }
            sortedResults.add(i + 1, toAdd);
        }
        return sortedResults;
    }

    private static <K, V extends Comparable<V>> void mergeSort(ArrayList<HashPair<K, V>> arr, int l, int r) {
        if (l < r) {
            int middle = (l + r) / 2;
            mergeSort(arr, l, middle);
            mergeSort(arr, middle + 1, r);

            int leftSubArraySize = middle - l + 1;
            ArrayList<HashPair<K, V>> leftSubArray = new ArrayList<HashPair<K, V>>();
            for (int i = 0; i < leftSubArraySize; i++)
                leftSubArray.add(arr.get(l + i));

            int rightSubArraySize = r - middle;
            ArrayList<HashPair<K, V>> rightSubArray = new ArrayList<HashPair<K, V>>();
            for (int i = 0; i < rightSubArraySize; i++)
                rightSubArray.add(arr.get(middle + 1 + i));

            int leftIndex = 0;
            int rightIndex = 0;
            int arrayIndex = l;
            while (leftIndex < leftSubArraySize && rightIndex < rightSubArraySize) {
                HashPair<K, V> fromLeft = leftSubArray.get(leftIndex);
                HashPair<K, V> fromRight = rightSubArray.get(rightIndex);
                if (fromLeft.getValue().compareTo(fromRight.getValue()) >= 0) {
                    arr.set(arrayIndex, fromLeft);
                    leftIndex++;
                } else {
                    arr.set(arrayIndex, fromRight);
                    rightIndex++;
                }
                arrayIndex++;
            }

            while (leftIndex < leftSubArraySize) {
                arr.set(arrayIndex, leftSubArray.get(leftIndex));
                leftIndex++;
                arrayIndex++;
            }

            while (rightIndex < rightSubArraySize) {
                arr.set(arrayIndex, rightSubArray.get(rightIndex));
                rightIndex++;
                arrayIndex++;
            }
        }
    }

    /**
     * This method takes as input an object of type MyHashTable with values that are
     * Comparable. It returns an ArrayList containing all the keys from the map,
     * ordered in descending order based on the values they mapped to.
     * 
     * The time complexity for this method is O(n*log(n)), where n is the number of
     * pairs in the map.
     */

    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        ArrayList<HashPair<K, V>> arr = new ArrayList<HashPair<K, V>>();
        for (HashPair<K, V> current : results)
            arr.add(current);

        mergeSort(arr, 0, arr.size() - 1);

        ArrayList<K> ret = new ArrayList<K>();
        for (HashPair<K, V> current : arr)
            ret.add(current.getKey());
        return ret;
    }

    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }

    private class MyHashIterator implements Iterator<HashPair<K, V>> {
        ArrayList<HashPair<K, V>> entries;
        int i = -1;

        /**
         * Expected average runtime is O(m) where m is the number of buckets
         */
        private MyHashIterator() {
            entries = new ArrayList<HashPair<K, V>>();
            for (LinkedList<HashPair<K, V>> bucket : buckets)
                entries.addAll(bucket);
        }

        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            i++;
            return i < entries.size();
        }

        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K, V> next() {
            return entries.get(i);
        }

    }
}
