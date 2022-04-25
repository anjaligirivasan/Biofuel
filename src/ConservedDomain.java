import java.io.*;
import java.util.*;

public class ConservedDomain
{
    public static ArrayList<Integer> countConserved(char[][] msa)
    {
        ArrayList<Integer> occurrenceIndex = new ArrayList<Integer>();
        for(int i = 0; i < 747; i++)
        {
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

    public static void getConserved(ArrayList<Integer> index, char[][] msa)
    {
        int start = index.get(0);
        int end = index.get(index.size()-1);
        int length = end - start; //361
        char[][] output = new char[27][length];
        int increase = 0;
        for(int i = 0; i < 27; i++) //rows
        {
            for(int j = 0; j < length; j++) //columns
            {
                output[i][j] = msa[i][start + increase];
                //System.out.println(start + increase);
                increase++;
            }
            increase = 0;
        }
        printSequences(output);
    }

    public static void printSequences(char[][] msa)
    {
        // print output of results to file
        try
        {
            PrintWriter output = new PrintWriter("conserved_phaB.txt");
            for (int i = 0; i < 27; i++) //rows
            {
                for (int j = 0; j < msa[0].length; j++) //columns
                {
                    output.print(msa[i][j]);
                }
                output.println();
            }
            output.close();
        }
        catch(FileNotFoundException exc)
        {
            System.out.printf("ERROR: %s\n", exc);
        }
    }

    public static void main(String args[])
    {
        // reading MSA file into a character array
        File file = new File("phaB.txt");
        String[] arr = new String[27];
        char[][] sequences = new char[27][750];
        try
        {
            Scanner scanner = new Scanner(file);
            for(int i = 0; i < sequences.length-1; i++)
            {
                arr[i] = scanner.nextLine();
                char[] temp = new char[750];
                temp = arr[i].toCharArray();
                for(int j = 0; j < temp.length-1; j++)
                {
                    sequences[i][j] = temp[j];
                    //System.out.print(sequences[i][j]);
                }
            }
            getConserved(countConserved(sequences), sequences);
        }
        catch(FileNotFoundException exc)
        {
            System.out.printf("ERROR: %s\n", exc);
        }
    }
}
