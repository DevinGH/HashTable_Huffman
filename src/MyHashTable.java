import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MyHashTable<Key, Value> {
    private final int capacity;
    private int size;
    private final Key[] keys;
    private final Value[] values;
    private int maxProbe = 0;
    private LinkedList<Key> keyList = new LinkedList<>();
    private ArrayList<Integer> histogram = new ArrayList<>();

    /**
     * o creates a hash table with capacity number of buckets (for this assignment you will
     * use capacity 215 = 32768)
     * o K is the type of the keys
     * o V is the type of the values
     */
    public MyHashTable(int capacity){
        this.capacity = capacity;
        keys = (Key[]) new Object[capacity];
        values = (Value[]) new Object[capacity];
        histogram.add(0);
    }

    /**
     * o update or add the newValue to the bucket hash(searchKey)
     * o if hash(key) is full use linear probing to find the next available bucket
     * @param searchKey
     * @param newValue
     */
    public void put(Key searchKey, Value newValue){
        if(searchKey != null && newValue != null){
            int keyHash = hash(searchKey);
            int numProbes = 0;

            while(keys[keyHash] != null && !keys[keyHash].equals(searchKey)){
                keyHash = (keyHash + 1) % capacity;
                numProbes++;
            }

            if(numProbes > maxProbe){
                for(int i = 0; i < (numProbes - maxProbe); i++){
                    histogram.add(0);
                }
                maxProbe = numProbes;
            }

            if(keys[keyHash] == null){
                int incrementEntry = histogram.get(numProbes) + 1;
                keys[keyHash] = searchKey;
                keyList.add(searchKey);
                size++;
                histogram.remove(numProbes);
                histogram.add(numProbes, incrementEntry);
            }

            values[keyHash] = newValue;
        }
    }

    /**
     o return a value for the specified key from the bucket hash(searchKey)
     o if  hash(searchKey)  doesn’t  contain  the  value  use  linear  probing  to  find  the
     appropriate value
     * @param searchKey
     * @return
     */
    public Value get(Key searchKey){
        Value output = null;
        int keyHash = hash(searchKey);

        if(searchKey != null){
            if(containsKey(searchKey)){
                while(!keys[keyHash].equals(searchKey)){
                    keyHash = (keyHash + 1) % capacity;
                }

                output = values[keyHash];
            }else{
                throw new ArrayStoreException(searchKey + " not in hash table");
            }
        }

        return output;
    }

    /**
     * o return true if there is a value stored for searchKey
     * @param searchKey
     * @return
     */
    public boolean containsKey(Key searchKey){
        if(keys[hash(searchKey)] != null && keyList.contains(searchKey)){
            return true;
        }
        return false;
    }

    /**
     * o a function that displays the following stat block for the data in your hash table:
     * o A histogram of probes shows how many keys are found after a certain number of
     * probes. In this example data there are 22687 words. 14749 of them could be found
     * in the bucket they belong in. 3525 of them could be found after one linear probe.
     * 1544  of  them  can  be found  after  two  linear  probes  and  so  on.  The  worst  key  is
     * found after 66 probes in my implementation, so my histogram has 66 entries.
     */
    public void stats(){
        System.out.println("Hash Table Stats");
        System.out.println("================");
        System.out.println("Number of Entries: " + size);
        System.out.println("Number of Buckets: " + capacity);
        System.out.print("Histogram of Probes: [");
        for(int i = 0; i < histogram.size() - 1; i++){
            System.out.print(histogram.get(i) + ", ");
        }
        System.out.print(histogram.get(histogram.size() - 1) + "]");
        System.out.println();
        System.out.println("Max Linear Probe: " + maxProbe);
        System.out.println("Fill Percentage: " + ((double)size/(double) capacity) * 100 + "%");
    }

    /**
     * o a private method that takes a key and returns an int in the range [0...capacity].
     * @param key
     * @return
     */
    private int hash(Key key){
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * o a method that converts the hash table contents to a String.
     * @return
     */
    public String toString(){
        StringBuilder string = new StringBuilder("[");
        int printed = 0;

        for(int i = 0; i < capacity; i++){
            if(keys[i] != null){
                if(printed != size - 1){
                    string.append("(" + keys[i] + ", " + values[i] + "), ");
                    printed++;
                }else{
                    string.append("(" + keys[i] + ", " + values[i] + ")");
                }
            }
        }

        string.append("]");

        return string.toString();
    }
}

