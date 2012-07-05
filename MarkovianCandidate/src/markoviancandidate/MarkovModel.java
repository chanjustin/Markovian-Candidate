package markoviancandidate;

/**
 *
 * @author Justin Chan
 * @version 1.0
 */
import java.util.HashMap;
import java.util.TreeSet;
public class MarkovModel 
{
    private int k;
    private String text;
    private TreeSet<Character> alphabet;
    private int alphabetSize;
    private HashMap<String,Integer> rawFrequencies;
    private HashMap<String, Double> processedFrequencies;
    private double totalLikelihood;
    private double averageLikelihood;
    
    public static void main(String args[])
    {
        int k = 2;
        String s = "aabcabaacaac";
        String newString = "aabca";
        
        MarkovModel model = new MarkovModel(k,s);
        System.out.println(model);
        System.out.printf("%.4f\n", model.laplace("aac"));
        System.out.printf("%.4f\n", model.laplace("aaa"));
        System.out.printf("%.4f\n", model.laplace("aab"));
        
        model.analyzeNewString(newString);
    }
    
    public MarkovModel(int k, String text)
    {
        this.k = k;
        this.text = text;
        getAlphabet();
        generateFrequencies();
    }
    
    public void analyzeNewString(String newString)
    {
        processedFrequencies = new HashMap<String, Double>();
        double totalLogLikelihood = 0;
        
        //System.out.printf("%10s %5s %15s", "context", "c", "log probability\n");
        for(int i = 0; i < newString.length(); i++)
        {
            String temp = getKGram(k+1,i,newString);
            double laplace = Math.log(laplace(temp));
            processedFrequencies.put(temp,laplace);
            
            totalLogLikelihood += laplace;
            //System.out.printf("%10s %5s %15.4f\n", getKGram(k,i,newString), temp.charAt(temp.length()-1), laplace);
        }
        totalLikelihood = totalLogLikelihood;
        averageLikelihood = totalLogLikelihood/newString.length();
        //System.out.printf("%s %.4f\n","TOTAL    log likelihood   =", totalLogLikelihood);
        //System.out.printf("%s %.4f\n","AVERAGE  log likelihood   =", totalLogLikelihood/newString.length());
    }
    
    public HashMap<String, Double> getFrequencies()
    {
        return processedFrequencies;
    }
    
    public double getTotalLikelihood()
    {
        return totalLikelihood;
    }
    
    public double getAverageLikelihood()
    {
        return averageLikelihood;
    }
    
    private void getAlphabet()
    {
        alphabet = new TreeSet<Character>();
        for(int i = 0; i < text.length(); i++)
        {
            alphabet.add(text.charAt(i));
        }
        alphabetSize = alphabet.size();
    }
    
    public double laplace(String s)
    {
        double temp = 1;
        double temp2 = alphabetSize;
        String substring = s.substring(0,s.length()-1);
        
        if(rawFrequencies.containsKey(s))
        {
            temp += rawFrequencies.get(s);
        }
        
        if(rawFrequencies.containsKey(substring))
        {
            temp2 += rawFrequencies.get(substring);
        }
        return (temp/temp2);
    }
    
    private void generateFrequencies()
    {
        rawFrequencies = new HashMap<String, Integer>();
        
        for(int i = 0; i < text.length(); i++)
        {
            String kGram = getKGram(k,i,text);
            
            if(rawFrequencies.containsKey(kGram))
            {
                int temp = rawFrequencies.get(kGram);
                rawFrequencies.put(kGram, ++temp);
            }
            else
            {
                rawFrequencies.put(kGram,1);
            }
        }
        
        for(int i = 0; i < text.length(); i++)
        {
            String kPlusOneGram = getKGram(k+1,i,text);
            
            if(rawFrequencies.containsKey(kPlusOneGram))
            {
                int temp = rawFrequencies.get(kPlusOneGram);
                rawFrequencies.put(kPlusOneGram, ++temp);
            }
            else
            {
                rawFrequencies.put(kPlusOneGram,1);
            }
        }
    }
    
    private String getKGram(int k, int number, String text)
    {
        String temp = "";
        if(number + k > text.length())
        {
            temp = text.substring(number,text.length());
            temp += text.substring(0,k-temp.length());
        }
        else
        {
            temp = text.substring(number,number+k);
        }
        return temp;
    }
    
    public String toString()
    {
        String temp = "S = " + alphabetSize + "\n";
        for(String string : rawFrequencies.keySet())
        {
            temp += "\"" + string + "\"" + "\t" + rawFrequencies.get(string) + "\n";
        }
        return temp;
    }
}
