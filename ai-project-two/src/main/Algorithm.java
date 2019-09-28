package main;

public class Algorithm {
    /* GA parameters */
    private final double uniformRate = 0.3;
    private final double mutationRate = 0.4;
    private final int tournamentSize = 5;
    private final boolean elitism = true;

    private FitnessFunction function = null;
    
    //Constructor
    public Algorithm(FitnessFunction f)
    {
    	function = f;
    }
    
    /* Public methods */
    
    // Evolve a population
    public Population evolvePopulation(Population pop) 
    {
        Population newPopulation = new Population(function, pop.size(), false);

        // Keep our best individual
        if (elitism) 
        {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) 
        {
            elitismOffset = 1;
        } 
        else 
        {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) 
        {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) 
        {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private Individual crossover(Individual indiv1, Individual indiv2) 
    {
        Individual candidate1 = new Individual(function);
        Individual candidate2 = new Individual(function);
        int limitBitOne = (int)Math.round(Math.random()*8);
        int limitGeneOne = (int)Math.round(Math.random()*3);
        int limitBitTwo = (int)Math.round(Math.random()*8);
        int limitGeneTwo = (int)Math.round(Math.random()*3);
        //Both limits should not be in the same gene.
        while(limitGeneTwo == limitGeneOne)
        {
        	limitGeneTwo = (int)Math.round(Math.random()*3);
        }
        int limits[][] = new int[][] { {0, 0},{0, 0} };
        if(limitGeneOne < limitGeneTwo)
        {
        	limits[0][0] = limitGeneOne;
        	limits[0][1] = limitBitOne;
        	limits[1][0] = limitGeneTwo;
        	limits[1][1] = limitBitTwo;
        }
        else
        {
        	limits[1][0] = limitGeneOne;
        	limits[1][1] = limitBitOne;
        	limits[0][0] = limitGeneTwo;
        	limits[0][1] = limitBitTwo;
        }
        // Loop through genes
        for (int i = 0; i < function.getNumVariables(); i++) 
        {
        	Integer[] tempGeneOne = {0,0,0,0,0,0,0,0};
        	Integer[] tempGeneTwo = {0,0,0,0,0,0,0,0};
        	for(int j = 0; j < 8; j++)
        	{
        		if(i == limits[0][0])
        		{
        			for(int k = 0; k < limits[0][1]; k++)
        			{
        				tempGeneOne[k] = indiv1.getGene(i)[k];
        				tempGeneTwo[k] = indiv2.getGene(i)[k];
        			}
        			for(int k = limits[0][1]; k < 8; k++)
        			{
        				tempGeneOne[k] = indiv2.getGene(i)[k];
        				tempGeneTwo[k] = indiv1.getGene(i)[k];
        			}
        		}
        		else if(i == limits[1][0])
        		{
        			for(int k = 0; k < limits[1][1]; k++)
        			{
        				tempGeneOne[k] = indiv2.getGene(i)[k];
        				tempGeneTwo[k] = indiv1.getGene(i)[k];
        			}
        			for(int k = limits[1][1]; k < 8; k++)
        			{
        				tempGeneOne[k] = indiv1.getGene(i)[k];
        				tempGeneTwo[k] = indiv2.getGene(i)[k];
        			}
        		}
        		else if(limits[0][0] < i && i < limits[1][0])
        		{
        			tempGeneOne = indiv2.getGene(i);
        			tempGeneTwo = indiv1.getGene(i);
        		}
        		else
        		{
        			tempGeneOne = indiv1.getGene(i);
        			tempGeneTwo = indiv2.getGene(i);
        		}
        	}
            // Crossover
            if (Math.random() <= uniformRate) 
            {
                candidate1.setGene(i, tempGeneOne);
                candidate2.setGene(i, tempGeneTwo);
            } 
            else
            {
            	candidate1.setGene(i, tempGeneTwo);
                candidate2.setGene(i, tempGeneOne);
            }
        }
        if(candidate1.getFitness() > candidate2.getFitness())
        {
        	return candidate1;
        }
        else
        {
        	return candidate2;
        }
        //System.out.println("Crossover: " + newSol.toString());
    }

    // Mutate an individual
    private void mutate(Individual indiv) 
    {
        // Loop through genes
        for (int i = 0; i < function.getNumVariables(); i++) 
        {
        	Integer[] gene = indiv.getGene(i);
            // Create random gene
        	for(int j = 0; j < 8; j++)
            {
        		if (Math.random() <= mutationRate) 
                {
        			if(gene[j] == 1)
        			{
        				gene[j] = 0;
        			}
        			else
        			{
        				gene[j] = 1;
        			}
                }
            }
            indiv.setGene(i, gene);
        }
        //System.out.println("Mutation: " + indiv.toString());
    }

    // Select individuals for crossover
    private Individual tournamentSelection(Population pop) 
    {
    	//TODO: Assign strength based probabilities to each individual.
        // Create a tournament population
        Population tournament = new Population(function, tournamentSize, false);
        tournament.saveIndividual(0, pop.getIndividual(0));
        // For each place in the tournament get a random individual
        for (int i = 1; i < tournamentSize; i++) 
        {
            int randomId = (int) (Math.random() * pop.size());
            while(pop.getIndividual(randomId).getFitness() < 0)
            {
            	randomId = (int) (Math.random() * pop.size());
            }
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        //System.out.println("Tournment Victor!: " + fittest.toString());
        return fittest;
    }

}
