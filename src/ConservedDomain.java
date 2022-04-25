import java.io.*;
import java.util.*;

/**
 * Function of this class is to find the conserved domain of
 * sequences using MSA of a specific gene sequence that is common
 * throughout various bacteria with the help of Clustal Omega
 */
public class ConservedDomain
{
    /**
     * This method looks through the column annotations made by Clustal Omega
     * to determine where possible start and end indexes could be for the domain
     * '*' infers conserved alignment, ':' infers strong similarity
     * and '.' infers weak similarity
     * we need at least four of those in a row to consider the index as a conserved domain
     * @param msa is the 2D character array with all sequences and alignments stored
     * @return the arraylist of possible start and end indexes
     */
    public static ArrayList<Integer> countConserved(char[][] msa)
    {
        ArrayList<Integer> occurrenceIndex = new ArrayList<Integer>();
        // loops through each element in the conservation rating row
        for(int i = 0; i < 747; i++)
        {
            // sets conditions where there needs to be at least four symbols in a row in order for
            // there to be a valid conserved domain index
            boolean cd1 = msa[25][i] == '*' | msa[25][i] == ':' | msa[25][i] == '.';
            boolean cd2 = msa[25][i+1] == '*' | msa[25][i+1] == ':' | msa[25][i+1] == '.';
            boolean cd3 = msa[25][i+2] == '*' | msa[25][i+2] == ':' | msa[25][i+2] == '.';
            boolean cd4 = msa[25][i+3] == '*' | msa[25][i+3] == ':' | msa[25][i+3] == '.';
            if(cd1 & cd2 & cd3 & cd4)
            {
                occurrenceIndex.add(i);
                //System.out.println("index: " + i + " alignment: " + msa[25][i]);
                i = i + 3;
            }
        }
        //System.out.println("First index: " + occurrenceIndex.get(0));
        //System.out.println("Last index: " + occurrenceIndex.get(occurrenceIndex.size()-1));
        return occurrenceIndex;
    }

    /**
     * This method extracts the conserved domains using the indexes stored in the arraylist
     * from the occurrenceIndex method.
     * It uses the first and last stored index to store a new 2D array
     * for just the conserved domain alignments
     * @param index is the arraylist storing the start and end indexes
     * @param msa is the 2D char array of the original multiple seq alignment
     */
    public static void getConserved(ArrayList<Integer> index, char[][] msa)
    {
        int start = index.get(0);
        int end = index.get(index.size()-1) + 4;
        int length = end - start;
        char[][] output = new char[27][length];
        // int that will move the start to the next index in line
        int increase = 0;
        for(int i = 0; i < 27; i++) //rows
        {
            for(int j = 0; j < length; j++) //columns
            {
                output[i][j] = msa[i][start + increase];
                //System.out.println(start + increase);
                increase++;
            }
            // resetting index to begin at start again
            increase = 0;
        }
        printSequences(output);
    }

    /**
     * This method simply creates a text file with whatever name we want
     * and runs through the 2D character array of conserved alignments
     * to output them onto the created text file instead of the console
     * @param conserved is the 2D array containing the aligned conserved
     *                  domains
     */
    public static void printSequences(char[][] conserved)
    {
        try
        {
            // creating new file to write to
            PrintWriter output = new PrintWriter("conserved_phaA.txt");
            for (int i = 0; i < 27; i++) //rows
            {
                for (int j = 0; j < conserved[0].length; j++) //columns
                {
                    output.print(conserved[i][j]);
                }
                output.println();
            }
            // closing the file writing
            output.close();
        }
        catch(FileNotFoundException exc)
        {
            System.out.printf("ERROR: %s\n", exc);
        }
    }

    /**
     * main method with method calls and MSA file creation
     * @param args
     */
    public static void main(String args[])
    {
        // calls information from preexisting file with MSA alignment
        File file = new File("phaA.txt");
        // String array to store each solo sequence
        String[] arr = new String[27];
        // 2D char array to store each sequence by amino acid
        char[][] sequences = new char[27][750];
        try
        {
            Scanner scanner = new Scanner(file);
            for(int i = 0; i < sequences.length-1; i++)
            {
                arr[i] = scanner.nextLine();
                char[] temp = new char[750];
                // convert solo seq in string to char array
                temp = arr[i].toCharArray();
                for(int j = 0; j < temp.length-1; j++)
                {
                    // insert amino acid one by one into 2D array
                    sequences[i][j] = temp[j];
                    //System.out.print(sequences[i][j]);
                }
            }
            // call methods on the MSA alignments to output the conserved domains
            getConserved(countConserved(sequences), sequences);
        }
        catch(FileNotFoundException exc)
        {
            System.out.printf("ERROR: %s\n", exc);
        }
    }
}
