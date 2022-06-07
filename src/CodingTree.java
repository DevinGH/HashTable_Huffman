import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CodingTree {
    private final int MAX_CAPACITY = 32768;

    /**
     * a hash table of words or separators used as keys to retrieve strings of 1s and 0s
     * as values.
     */
    private MyHashTable<String, String> codes = new MyHashTable<>(MAX_CAPACITY);

    /**
     * o a data member that is the message encoded using the Huffman codes.
     */
    private String bits;

    /**
     * Priority queue that stores the Huffman tree
     */
    private PriorityQueue<HuffmanNode> tree = new PriorityQueue<>();

    /**
     * A hash table that contains words and separators with their respected frequencies
     */
    private MyHashTable<String, Integer> frequencies = new MyHashTable<>(MAX_CAPACITY);

    /**
     * A string that stores the original text string
     */
    private String text;

    /**
     * A linked list that stores the entire list of individual words and separators
     */
    private LinkedList<String> wordsAndSeparators = new LinkedList<>();

    /**
     * A linked list that stores every individual key
     */
    private LinkedList<String> keys = new LinkedList<>();

    /**
     * Linked list that stores every key encoded form
     */
    private LinkedList<String> encodedKeys = new LinkedList<>();

    /**
     * Hash table that stores the encoded key with it's decoded self
     */
    private MyHashTable<String, String> decodeCodes = new MyHashTable<>(MAX_CAPACITY);

    /**
     * o a constructor that takes the text of an English message to be compressed.
     * o The constructor is responsible for calling all methods that carry out the Huffman
     * coding algorithm and ensuring that the following property has the correct value.
     * @param fulltext
     */
    public CodingTree(String fulltext) throws IOException{
        //double startTime = System.currentTimeMillis();
        this.text = fulltext;
        //System.out.println("Got Text");
        parseWords();
        //System.out.println("Parsed Words");
        //double endTime = System.currentTimeMillis();
        //System.out.println("Parsed Words in: " + (endTime - startTime)/1000 + " seconds");
        countFrequencies();
        //System.out.println("Got Frequencies");
        //endTime = System.currentTimeMillis();
        //System.out.println("Got Frequencies in: " + (endTime - startTime)/1000 + " seconds");
        createTree();
        //System.out.println("Created Tree");
        //endTime = System.currentTimeMillis();
       // System.out.println("Created Tree in: " + (endTime - startTime)/1000 + " seconds");
        encode();
        //System.out.println("Encoded Text");
        //endTime = System.currentTimeMillis();
        //System.out.println("Encoded Text in: " + (endTime - startTime)/1000 + " seconds");
        createFiles();
       // System.out.println("Created Files");
       // endTime = System.currentTimeMillis();
        //System.out.println("Created Files in: " + (endTime - startTime)/1000 + " seconds");
        codes.stats();
    }

    /**
     * Separates out every word and separator into a linked list
     */
    private void parseWords(){
        char[] allChars = text.toCharArray();

        StringBuilder currentWord  = new StringBuilder();
        boolean isWord = false;

        for(int i = 0; i < allChars.length; i++){
            if(isWordChar(allChars[i])){
                currentWord.append(allChars[i]);
                isWord = true;
            }
            if(!isWordChar(allChars[i]) || i == allChars.length - 1){
                if(isWord){
                    wordsAndSeparators.add(currentWord.toString());
                    isWord = false;
                    currentWord = new StringBuilder();
                }

                if(i != allChars.length - 1){
                    currentWord.append(allChars[i]);
                    wordsAndSeparators.add(currentWord.toString());
                    currentWord = new StringBuilder();
                }
            }
        }
    }

    /**
     * Counts the frequencies of each word and separator
     */
    private void countFrequencies(){
        for(String str : wordsAndSeparators){
            if(frequencies.containsKey(str)){
                int newValue = frequencies.get(str) + 1;
                frequencies.put(str, newValue);

            }else{
                frequencies.put(str, 1);
                keys.add(str);
            }
        }
    }

    /**
     * Creates the Huffman tree based on the frequencies
     */
    private void createTree(){
        for(String str : keys){
                tree.add(new HuffmanNode(str, frequencies.get(str)));
        }

        while(tree.size() > 1){
            tree.add(new HuffmanNode(tree.poll(), tree.poll()));
        }

        getCodes(tree.peek(), "");
    }

    /**
     * Creates and writes each code for words and separators to a hash table
     */
    private void getCodes(HuffmanNode root, String code){
        if(root.getLeftN() != null){
            getCodes(root.getLeftN(), code + "0");
        }
        if(root.getRightN() != null){
            getCodes(root.getRightN(), code + "1");
        }
        if(root.getRightN() == null && root.getLeftN() == null){
            codes.put(root.getWord(), code);
            decodeCodes.put(code, root.getWord());
        }
    }

    /**
     * Encodes the original text into binary
     * @return
     */
    private BitSet encode(){
        StringBuilder encodedText = new StringBuilder();

        for(String str : wordsAndSeparators){
            encodedText.append(codes.get(str));
            encodedKeys.add(codes.get(str));
        }

        bits = encodedText.toString();

        while(encodedText.toString().length() % 8 != 0){
            encodedText.append("1");
        }

        BitSet outputBitSet = new BitSet(encodedText.toString().length());
        int bitCounter = 0;

        for(Character c : encodedText.toString().toCharArray()){
            if(c.equals('1')){
                outputBitSet.set(bitCounter);
            }
            bitCounter++;
        }

        return outputBitSet;
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
        StringBuilder decodedText = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();

        for(Character c : bits.toCharArray()){
            currentWord.append(c);

            if(codes.containsKey(currentWord.toString())){
                decodedText.append(codes.get(currentWord.toString()));

                currentWord = new StringBuilder();
            }
        }

        return decodedText.toString();
    }

    /**
     * Creates and writes to the output files
     * @throws IOException
     */
    private void createFiles() throws IOException {
        //System.out.println("Got to start of createFile");
        FileOutputStream encodedText = new FileOutputStream("compressed2.txt");
        FileWriter codes = new FileWriter("codes2.txt");
        FileWriter decodedText = new FileWriter("decoded2.txt");

        //System.out.println("Got to start of writing the files");
        encodedText.write(encode().toByteArray());
        long encodedSize = Files.size(Paths.get("compressed2.txt")) / 1024;
        //System.out.println("finished encoding");
        codes.write(this.codes.toString());
        //System.out.println("finished codes");
        decodedText.write(decode(bits, decodeCodes));
        long decodedSize = Files.size(Paths.get("decoded2.txt")) / 1024;
        //System.out.println("finished decoding");

        encodedText.close();
        codes.close();
        decodedText.close();
        System.out.println("Size of encoded file: " + encodedSize);
        System.out.println("Size of decoded file: " + decodedSize);
        //System.out.println("finished writing files");
    }
}

/**
 * Self-made node class that follows the properties of the nodes in a Huffman Tree
 */
class HuffmanNode implements  Comparable<HuffmanNode>{
    private Integer weight;

    private String word;

    private HuffmanNode leftN;

    private HuffmanNode rightN;

    /**
     * Constructor for leaf nodes that contain a word
     * @param word
     * @param weight
     */
    public HuffmanNode(String word, Integer weight){
        this.word = word;
        this.weight = weight;
    }

    /**
     * Constructor that combines two other nodes based on their weight(frequencies)
     * @param leftN
     * @param rightN
     */
    public HuffmanNode(HuffmanNode leftN, HuffmanNode rightN){
        this.leftN = leftN;
        this.rightN = rightN;

        this.weight = leftN.weight + rightN.weight;
    }

    /**
     * Returns the left child node of the current parent node
     * @return
     */
    public HuffmanNode getLeftN(){
        return this.leftN;
    }

    /**
     * Returns the right child node of the current parent node
     * @return
     */
    public HuffmanNode getRightN(){
        return this.rightN;
    }

    /**
     * Returns the word contained in a leaf node
     * @return
     */
    public String getWord(){
        return this.word;
    }
    @Override
    public int compareTo(HuffmanNode other){
        return this.weight.compareTo(other.weight);
    }
}