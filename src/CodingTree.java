import java.util.*;

public class CodingTree {
    /**
     * a hash table of words or separators used as keys to retrieve strings of 1s and 0s
     * as values.
     */
    private MyHashTable<String, String> codes = new MyHashTable<>(100);

    /**
     * o a data member that is the message encoded using the Huffman codes.
     */
    private List<Byte> bits;
    private PriorityQueue<HuffmanNode> tree = new PriorityQueue<>();
    private MyHashTable<String, Integer> frequencies = new MyHashTable<>(100);
    private String text;
    private LinkedList<String> wordsAndSeparators = new LinkedList<>();
    private LinkedList<String> keys = new LinkedList<>();

    /**
     * o a constructor that takes the text of an English message to be compressed.
     * o The constructor is responsible for calling all methods that carry out the Huffman
     * coding algorithm and ensuring that the following property has the correct value.
     * @param fulltext
     */
    public CodingTree(String fulltext){
        this.text = fulltext;
        parseWords();
        countFrequencies();
        createTree();
        encode();
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
        System.out.println(keys.toString());

        for(String str : keys){
                tree.add(new HuffmanNode(str, frequencies.get(str)));
        }

        while(tree.size() > 1){
            tree.add(new HuffmanNode(tree.poll(), tree.poll()));
        }

        getCodes(tree.peek(), "");

        System.out.println(codes.toString());
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
        }
    }

    private void encode(){
        StringBuilder encodedText = new StringBuilder();

        for(String str : wordsAndSeparators){
            encodedText.append(codes.get(str));
        }

        System.out.println(encodedText.toString());

        while(encodedText.toString().length() % 8 != 0){
            encodedText.append("1");
        }

        System.out.println(encodedText.toString());

        BitSet outputBitSet = new BitSet(encodedText.toString().length());
        int bitCounter = 0;

        for(Character c : encodedText.toString().toCharArray()){
            if(c.equals('1')){
                outputBitSet.set(bitCounter);
            }
            bitCounter++;
        }

        System.out.println(outputBitSet.toString());
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

    private void createFiles(){

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

    public HuffmanNode getLeftN(){
        return this.leftN;
    }

    public HuffmanNode getRightN(){
        return this.rightN;
    }

    public String getWord(){
        return this.word;
    }
    @Override
    public int compareTo(HuffmanNode other){
        return this.weight.compareTo(other.weight);
    }
}