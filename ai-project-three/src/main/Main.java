package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		//Prompt the user to enter a file name.
		System.out.print("Please enter the name of the text file with the Sudoku Board: ");
		Scanner scanner = new Scanner(System.in);
		String sudokuBoardName = scanner.nextLine();   
		String sudokuBoardPath = "tests/" + sudokuBoardName; //Java starts at the root of the project.
		System.out.println("");
	    
	    //Open the file and the read in the information about the function.
		Board newBoard = null;
		File sudokuBoardFile = new File(sudokuBoardPath);
		try
		{
			scanner = new Scanner(sudokuBoardFile);
		} 
		catch(FileNotFoundException e1)
		{
			System.out.println("File was not found. Please enter the name of a file that exists.");
			e1.printStackTrace();
		}
		finally
		{
			System.out.println(sudokuBoardName);
		    System.out.println("---------------");
			String num = "";
			ArrayList<Integer> sudokuBoardInts = new ArrayList<Integer>();
			while(scanner.hasNext())
			{
				String temp = scanner.next();
				num = temp;
				sudokuBoardInts.add(Integer.parseInt(num));
				num = "";
			}
			newBoard = new Board(sudokuBoardInts);
			scanner.close();
			System.out.println(newBoard.toString());
		}

		Algorithm algorithm = new Algorithm(newBoard);
		
		algorithm.runAlgorithm();
	}

}
