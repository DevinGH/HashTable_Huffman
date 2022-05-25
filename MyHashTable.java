public class MyHashTable<K, V> {
    /**
     * o creates a hash table with capacity number of buckets (for this assignment you will
     * use capacity 215 = 32768)
     * o K is the type of the keys
     * o V is the type of the values
     */
    public MyHashTable<K, V>(int capacity){

    }

    /**
     * o update or add the newValue to the bucket hash(searchKey)
     * o if hash(key) is full use linear probing to find the next available bucket
     * @param searchKey
     * @param newValue
     */
    public void put(K searchKey, V newValue){

    }

    /**
     o return a value for the specified key from the bucket hash(searchKey)
     o if  hash(searchKey)  doesn’t  contain  the  value  use  linear  probing  to  find  the
     appropriate value
     * @param searchKey
     * @return
     */
    public V get(K searchKey){

    }

    /**
     * o return true if there is a value stored for searchKey
     * @param searchKey
     * @return
     */
    public boolean containsKey(K searchKey){

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

    }

    /**
     * o a private method that takes a key and returns an int in the range [0...capacity].
     * @param key
     * @return
     */
    private int hash(K key){

    }

    /**
     * o a method that converts the hash table contents to a String.
     * @return
     */
    public String toString(){

    }
}
