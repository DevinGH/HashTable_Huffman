import java.util.*;

public class CodingTree {
    /**
     * a hash table of words or separators used as keys to retrieve strings of 1s and 0s
     * as values.
     */
    private MyHashTable<String, String> codes;

    /**
     * o a data member that is the message encoded using the Huffman codes.
     */
    private List<Byte> bits;

    private MyHashTable<String, Integer> frequencies = new MyHashTable<>(100);

    private String text;

    private LinkedList<String> wordsAndSeparators = new LinkedList<>();

    /**
     * o a constructor that takes the text of an English message to be compressed.
     * o The constructor is responsible for calling all methods that carry out the Huffman
     * coding algorithm and ensuring that the following property has the correct value.
     * @param fulltext
     */
    public CodingTree(String fulltext){
        this.text = fulltext;
        parseWords();
        System.out.println(wordsAndSeparators.toString());
        System.out.println(frequencies.toString());
    }

    /**
     * Separates out every word and separator into a linked list
     */
    private void parseWords(){
        char[] allChars = text.toCharArray();

        StringBuilder currentWord  = new StringBuilder();
        boolean isWord = false;

        for(Character c : allChars){
            if(isWordChar(c)){
                currentWord.append(c);
                isWord = true;
            }else{
                if(isWord){
                    wordsAndSeparators.add(currentWord.toString());
                    isWord = false;
                    currentWord = new StringBuilder();
                }

                wordsAndSeparators.add(c.toString());
            }
        }

        countFrequencies();
    }

    /**
     * Counts the frequencies of each word and separator
     */
    private void countFrequencies(){
        for(String str : wordsAndSeparators){
            if(frequencies.containsKey(str)){
                int prevValue = frequencies.get(str);

                frequencies.put(str, prevValue + 1);
            }else{
                frequencies.put(str, 1);
            }
        }
    }

    /**
     * Creates the Huffman tree based on the frequencies
     */
    private void createTree(){
        PriorityQueue<HuffmanNode> tree = new PriorityQueue<>();
    }

    /**
     * Creates and writes each code for words and separators to a hash table
     */
    private void getCodes(){

    }

    /**
     * Checks to see if a char could be part of a word
     * @param ch
     * @return
     */
    private boolean isWordChar(Character ch){
        return ((ch.compareTo('a') >= 0 && ch.compareTo('z') <= 0) ||
                (ch.compareTo('A') >= 0 && ch.compareTo('Z') <= 0) ||
                (ch.compareTo('0') >= 0 && ch.compareTo('9') <= 0) ||
                ch.compareTo('\'') == 0 || ch.compareTo('-') == 0);
    }

    /**
     * this method will take the
     * output of Huffman’s encoding and produce the original text.
     * @param bits
     * @param codes
     * @return
     */
    public String decode(String bits, MyHashTable<String, String> codes){
        return "";
    }
}

class HuffmanNode implements  Comparable<HuffmanNode>{
    private Integer weight;

    private String word;

    private HuffmanNode leftN;

    private HuffmanNode rightN;

    public HuffmanNode(String word, Integer weight){
        this.word = word;
        this.weight = weight;
    }

    public HuffmanNode(HuffmanNode leftN, HuffmanNode rightN){
        this.leftN = leftN;
        this.rightN = rightN;

        this.weight = leftN.weight + rightN.weight;
    }
    @Override
    public int compareTo(HuffmanNode other){
        return this.weight.compareTo(other.weight);
    }
}