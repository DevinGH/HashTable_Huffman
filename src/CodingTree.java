import java.util.*;

public class CodingTree {
    /**
     * a hash table of words or separators used as keys to retrieve strings of 1s and 0s
     * as values.
     */
    public MyHashTable<String, String> codes;

    /**
     * o a data member that is the message encoded using the Huffman codes.
     */
    public List<Byte> bits;

    /**
     * o a constructor that takes the text of an English message to be compressed.
     * o The constructor is responsible for calling all methods that carry out the Huffman
     * coding algorithm and ensuring that the following property has the correct value.
     * @param fulltext
     */
    public CodingTree(String fulltext){

    }

    /**
     * this method will take the
     * output of Huffman’s encoding and produce the original text.
     * @param bits
     * @param codes
     * @return
     */
    public String decode(String bits, Map<String, String> codes){
        return "";
    }
}