package main;

import java.util.ArrayList;

public class FitnessFunction 
{
    private int numVariables;
    
    private ArrayList<Integer> functionCoefficients = null;
    
    //Constructor
    public FitnessFunction(ArrayList<Integer> f)
    {
    	functionCoefficients = f;
    	this.numVariables = functionCoefficients.get(0);
    }
    
    //Getters
    public int getNumVariables()
    {
    	return numVariables;
    }
    
    //Public Functions
    public int exp(int a, int b)
    {
        int result = 1;
        for (int i = 0; i < b; i++) 
        {
            result *= a;
        }
        return result;
    }
    public int findFitness(Individual individual)
    {
        int fitness = 0;
        ArrayList<Integer[]> genes = new ArrayList<Integer[]>();
        Integer[] temp = {0,0,0,0,0,0,0,1}; //This is for the first variable used this the calculations.
        genes.add(temp);
        // Loop through our individuals genes and compare them to our candidates
        for (int i = 0; i < numVariables; i++) 
        {
            genes.add(individual.getGene(i)); 
        }
        System.out.println(individual.toString());
        int index = 1;
        for(int j = 0; j <= numVariables; j++)
        {
        	for(int i = j; i <= numVariables; i++)
        	{
        		int geneTempI = 0;
        		for(int tempI = 0; tempI < 8; tempI++)
        		{
        			geneTempI += genes.get(i)[tempI]*exp(2,7-tempI);
        		}
        		int geneTempJ = 0;
        		for(int tempJ = 0; tempJ < 8; tempJ++)
        		{
        			geneTempJ += genes.get(j)[tempJ]*exp(2,7-tempJ);
        		}
        		fitness += geneTempI*geneTempJ*functionCoefficients.get(index);
        		index++;
        	}
        	index += j+1;
        }
        return fitness;
    }
    
    //Overridden Object Functions
    @Override
    public String toString()
    {
    	String temp = "";
    	
    	temp += numVariables;
    	temp += System.lineSeparator();
    	
    	int index = 1;
    	for(int i = 0; i <= numVariables; i++)
    	{
	    	for(int j = 0; j <= numVariables; j++)
	    	{
				temp += functionCoefficients.get(index);
				temp += " ";
				if(j == numVariables)
				{
					temp += System.lineSeparator();
				}
				index++;
	    	}
    	}
    	
    	return temp;
    }
}
