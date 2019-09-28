package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main 
{
	final static int POPULATION = 50;
	
	final static int MAX_GENERATIONS = 100;
	
	public static FitnessFunction function = null;
	
	public static Algorithm algorithm = null;
	
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		//Prompt the user to enter a file name.
		System.out.print("Please enter the name of the text file with the function: ");
		Scanner scanner = new Scanner(System.in);
		String functionFileName = scanner.nextLine();   
		System.out.println("");
	    
	    //Open the file and the read in the information about the function.
	    //NOTE: Java begins searching for the text file at the root of the project.
		File functionFile = new File(functionFileName);
		try
		{
			scanner = new Scanner(functionFile);
		} 
		catch(FileNotFoundException e1)
		{
			System.out.println("File was not found. Please entire the name of a file that exists.");
			e1.printStackTrace();
		}
		finally
		{
			System.out.println(functionFileName);
		    System.out.println("---------------");
			String num = "";
			ArrayList<Integer> functionCoefficients = new ArrayList<Integer>();
			while(scanner.hasNext())
			{
				String temp = scanner.next();
				num = temp;
				functionCoefficients.add(Integer.parseInt(num));
				num = "";
			}
			function = new FitnessFunction(functionCoefficients);
			scanner.close();
			System.out.println(function.toString());
		}
		
		algorithm = new Algorithm(function);

        // Create an initial population
        Population myPop = new Population(function, POPULATION, true);
        System.out.println(myPop.toString());
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while (generationCount < MAX_GENERATIONS) 
        {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = algorithm.evolvePopulation(myPop);
            System.out.println(myPop.toString());
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
        System.out.println("Genes:");
        System.out.println(myPop.getFittest().toString());
	}
}
