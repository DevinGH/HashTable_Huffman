import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    /**
     * this  method  will  carry  out  compression  of  a  file  using  the
     * CodingTree class.
     * o Read in from a text file. The filename must be passed as a command line argument
     * to the program, so it will be available as args[0].
     * o Output to two files. Again, feel free to hardcode the names of these files into your
     * program. These are the codes text file and the compressed binary file.
     * o Display statistics. You must output the original size (in kibibytes), the compressed
     * size (in kibibytes), the compression ratio (as a percentage), the elapsed time for
     * compression, and the hash table statistics.
     * @param args
     */
    public static void main(String[] args) throws  IOException{
        double startTime = System.currentTimeMillis();
        Scanner inputfile = new Scanner(new File(args[0]));
        long orignalSize = Files.size(Paths.get(args[0])) / 1024;
        System.out.println("Size of original file: " + orignalSize);
        System.out.println();

        String textString = inputfile.useDelimiter("\\A").next();
        CodingTree huffman = new CodingTree(textString);

        double endTime = System.currentTimeMillis();
        System.out.println("Completed Program in: " + (endTime - startTime) + " milliseconds");

        /**
         * Methods used for testing
         */
        //Main test = new Main();
        //test.testCodingTree();
        //test.testMyHashTable();
    }

    /**
     * o this method tests the  coding  tree  on  a  short  simple  phrase, so  you  can  verify  its
     * correctness.
     */
    public void testCodingTree() throws IOException {
        String testMessage = "Yo wahts up! I want to see the stars. Do you? you want some up stars? cause that pleases me you you you you . stars";

        CodingTree tree = new CodingTree(testMessage);
    }

    /**
     * o this method tests the hash table.
     */
    public void testMyHashTable(){
        MyHashTable<String, String> testtable = new MyHashTable<String, String>(10000);

        for(int i = 0; i < 10000; i++){
            testtable.put("" + i, "" + (i+1));
        }

        //System.out.println(testtable.toString());
        //System.out.println(testtable.containsKey("no"));
        //System.out.println(testtable.get("Maybe"));
        //System.out.println(testtable.get("maybe"));  //Example of key not being in table
        testtable.stats();
    }
}
