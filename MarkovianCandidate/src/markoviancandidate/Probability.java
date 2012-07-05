package markoviancandidate;

/**
 *
 * @author Justin Chan
 * @version 1.0
 */

import java.text.DecimalFormat;
public class Probability implements Comparable
{
    private String characterContext;
    private double modelOneProbability;
    private double modelTwoProbability;
    private String absProbability;
    private DecimalFormat format;
    
    public Probability(String characterContext, double modelOneProbability, double modelTwoProbability)
    {
        format = new DecimalFormat("#.###");
        String sign = modelOneProbability - modelTwoProbability > 0 ? "+" : "";
        this.characterContext = characterContext;
        this.modelOneProbability = modelOneProbability;
        this.modelTwoProbability = modelTwoProbability;
        this.absProbability = sign + format.format(modelOneProbability - modelTwoProbability);
    }
    
    public String toString()
    {
        String firstProbability = format.format(modelOneProbability);
        String secondProbability = format.format(modelTwoProbability);
        
        String representation = "\"" + getCharacterContext() + "\"\t" + firstProbability + "\t" +  secondProbability + "\t" + absProbability;
        
        return representation;
    }
    
    public int compareTo(Object o)
    {
        Probability p = (Probability)o;
        
        if(Math.abs(modelOneProbability - modelTwoProbability) > Math.abs(p.getModelOneProbability() - p.getModelTwoProbability()))
        {
            return 1;
        }
        else if(Math.abs(modelOneProbability - modelTwoProbability) == Math.abs(p.getModelOneProbability() - p.getModelTwoProbability()))
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }

    /**
     * @return the characterContext
     */
    public String getCharacterContext() {
        return characterContext;
    }

    /**
     * @param characterContext the characterContext to set
     */
    public void setCharacterContext(String characterContext) {
        this.characterContext = characterContext;
    }

    /**
     * @return the modelOneProbability
     */
    public double getModelOneProbability() {
        return modelOneProbability;
    }

    /**
     * @param modelOneProbability the modelOneProbability to set
     */
    public void setModelOneProbability(double modelOneProbability) {
        this.modelOneProbability = modelOneProbability;
    }

    /**
     * @return the modelTwoProbability
     */
    public double getModelTwoProbability() {
        return modelTwoProbability;
    }

    /**
     * @param modelTwoProbability the modelTwoProbability to set
     */
    public void setModelTwoProbability(double modelTwoProbability) {
        this.modelTwoProbability = modelTwoProbability;
    }
}
