package markoviancandidate;

/**
 *
 * @author Justin Chan
 * @version 1.0
 */

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Arrays;

public class BestModel 
{   
    public static void main(String args[]) throws Exception
    {
        new BestModel(2,"src/texts/bush-kerry/bush1+2.txt","src/texts/bush-kerry/kerry1+2.txt","src/texts/bush-kerry/bush3-00.txt");
    }
    
    public BestModel(int k, String firstModelFilePath, String secondModelFilePath, String testTextFilePath) throws Exception
    {
        String firstModelText = "";
        String secondModelText = "";
        String testText = "";
        
        Scanner input = new Scanner(new BufferedReader(new FileReader(firstModelFilePath)));
        input.useDelimiter("\\A");
        while(input.hasNext())
        {
            firstModelText += input.next();
        }
        
        input = new Scanner(new BufferedReader(new FileReader(secondModelFilePath)));
        input.useDelimiter("\\A");
        while(input.hasNext())
        {
            secondModelText += input.next();
        }
        
        input = new Scanner(new BufferedReader(new FileReader(testTextFilePath)));
        input.useDelimiter("\\A");
        while(input.hasNext())
        {
            testText += input.next();
        }
        
        MarkovModel firstModel = new MarkovModel(k,firstModelText);
        MarkovModel secondModel = new MarkovModel(k,secondModelText);
        
        firstModel.analyzeNewString(testText);
        secondModel.analyzeNewString(testText);
        
        double probability = firstModel.getAverageLikelihood()-secondModel.getAverageLikelihood();
        System.out.printf("%.4f %.4f +%.4f\n",firstModel.getAverageLikelihood(),secondModel.getAverageLikelihood(),probability);
        if(probability > 0)
        {
            System.out.println("First model is more likely.\n");
        }
        else
        {
            System.out.println("Second model is more likely\n");
        }
        printDetails(firstModel, secondModel);
    }
    
    public void printDetails(MarkovModel firstModel, MarkovModel secondModel)
    {
        LinkedList<Probability> list = new LinkedList<Probability>();
        for(String s : firstModel.getFrequencies().keySet())
        {
            if(secondModel.getFrequencies().get(s) != null)
            {
                Probability temp = new Probability(s,firstModel.getFrequencies().get(s),secondModel.getFrequencies().get(s));
                list.add(temp);
            }
        }
        Object[] tempArray = list.toArray();
        Arrays.sort(tempArray);
        for(int i = tempArray.length-1; i > tempArray.length-11; i--)
        {
            System.out.println(tempArray[i]);
        }
    }
}