package main;

public class Population 
{
	
	private Individual[] individuals;

	private FitnessFunction function = null;
	
    /*
     * Constructors
     */
    // Create a population
    public Population(FitnessFunction f, int populationSize, boolean initialise) 
    {
    	function = f;
        individuals = new Individual[populationSize];
        // Initialize population
        if (initialise) 
        {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) 
            {
            	Individual newIndividual = new Individual(function);
                //newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
    }

    /* Getters */
    public Individual getIndividual(int index) 
    {
        return individuals[index];
    }

    public Individual getFittest() 
    {
        Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) 
        {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) 
            {
                fittest = getIndividual(i);
            }
        }
        //System.out.println("Save Fittest: " + fittest.toString());
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() 
    {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) 
    {
        individuals[index] = indiv;
        //System.out.println("Save: " + indiv.toString());
    }
    
    public String toString()
    {
    	String pop = "";
    	for(int i = 0; i < this.size(); i++)
    	{
    		pop += individuals[i].toString();
    		if(individuals[i] ==  this.getFittest())
    		{
    			pop += " - Fittest";
    		}
    		pop += System.lineSeparator();
    	}
    	return pop;
    }

}
