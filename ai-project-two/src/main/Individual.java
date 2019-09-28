package main;

import java.util.ArrayList;

public class Individual 
{
	private ArrayList<Integer[]> DNA = new ArrayList<Integer[]>();
	
	private FitnessFunction function = null;
	
	private int fitness = 0;
	
	//Constructor
	public Individual(FitnessFunction f)
	{
		function = f;
		
        for (int i = 0; i < function.getNumVariables(); i++) 
        {
        	Integer[] temp = {0,0,0,0,0,0,0,0};
            for(int j = 0; j < 8; j++)
            {
            	temp[j] = (int)Math.round(Math.random());
            }
            DNA.add(temp);
        }
        this.fitness = function.findFitness(this);
        //System.out.println("New: " + this.toString());
	}
	
	//Getters
	public Integer[] getGene(int index)
	{
		return DNA.get(index);
	}
	public int getFitness()
	{
		return fitness;
	}
	
	//Setters
	public void setGene(int index, Integer[] gene) 
    {
        DNA.set(index, gene);
        this.fitness = function.findFitness(this);
    }
	
    @Override
    public String toString() 
    {
        String geneString = "";
        for (int i = 0; i < DNA.size(); i++) 
        {
        	//geneString += String.format("%8s", Integer.toBinaryString((int)getGene(i))).replace(' ', '0');
        	for(int j = 0; j < 8; j++)
        	{
        		geneString += getGene(i)[j];
        	}
        	geneString += " ";
        }
        return geneString;
    }
	
	/*
	 static int defaultGeneLength = 64;
	    private byte[] genes = new byte[defaultGeneLength];
	    // Cache
	    private int fitness = 0;

	    // Create a random individual
	    public void generateIndividual() 
	    {
	        for (int i = 0; i < size(); i++) 
	        {
	            byte gene = (byte) Math.round(Math.random());
	            genes[i] = gene;
	        }
	    }
*/
	    /* Getters and setters */
	    // Use this if you want to create individuals with different gene lengths
/*	    public static void setDefaultGeneLength(int length) 
	    {
	        defaultGeneLength = length;
	    }
	    
	    public byte getGene(int index) 
	    {
	        return genes[index];
	    }

	    public void setGene(int index, byte value) 
	    {
	        genes[index] = value;
	        fitness = 0;
	    }*/

	    /* Public methods */
	/*    public int size() 
	    {
	        return genes.length;
	    }

	    public int getFitness() 
	    {
	        if (fitness == 0) {
	            fitness = FitnessCalc.getFitness(this);
	        }
	        return fitness;
	    }

	    @Override
	    public String toString() 
	    {
	        String geneString = "";
	        for (int i = 0; i < size(); i++) {
	            geneString += getGene(i);
	        }
	        return geneString;
	    }
*/
}
