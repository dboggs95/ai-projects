package AIProjectFive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * The Driver Java file contains Main file and builds the tree
 * Also includes calculations for information gain and prints the tree
 */

public class Main 
{
	private static ArrayList<String> attributes;
	private static ArrayList<Node> tree = new ArrayList<Node>();
 
	public static void main( String args[] ) throws FileNotFoundException 
	{
		String fileName = getFileName();
		ArrayList<String> fileData = readFile( fileName );
		ArrayList<Instance> examples = createExamples( fileData );
		Node root = buildDecisionTree(examples, attributes);
		displayTree(root); 
	}
	
	//================================
	//------   Begin File IO    ------
	//================================
	
	public static String getFileName()
	{
		System.out.println("Please enter a file name: ");
		Scanner input = new java.util.Scanner(System.in);	
		String fileName = input.nextLine();
		input.close();
		return fileName;
	}
	
	public static File openFile( String fileName )
	{
		File file = null;
		try
		{
			file = new File( fileName );
			if( !file.exists() )
			{
				System.out.println("This file does not exist.");
				System.exit(0);
			}
		}
		catch( Exception e ){}				
		return file;
	}
	
	// Reads file data and returns it in an ArrayList
	public static ArrayList<String> readFile( String fileName ) throws FileNotFoundException
	{
		File file = openFile( fileName );
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
		ArrayList<String> fileLines = new ArrayList<String>();
		while( scanner.hasNext() )
		{
			fileLines.add( scanner.nextLine( ) );
		}
		scanner.close();
		return fileLines;
	}
	
	//Makes the examples from the data ArrayList
	public static ArrayList<Instance> createExamples(ArrayList<String> fileData)
	{
		attributes = new ArrayList<String>();
		int count = 0;
		while( !fileData.get( count ).equals("") )
		{
			attributes.add( fileData.get( count ) );
			count++;
		}		
		// Accounts for blank line in file
		count++;
			
		// Create the ArrayList
		ArrayList<Instance> examples = new ArrayList<Instance>();
		while( count < fileData.size() )
		{
			String[] lineData = fileData.get( count ).split(",");
			ArrayList<Integer> line = new ArrayList<Integer>();
			for(int i = 0; i < lineData.length; i++)
			{
				line.add( Integer.parseInt( lineData[i] ));
			}
			examples.add( new Instance( attributes, line ) );
			count++;
		}
		return examples;
	}
	
	//================================
	//------	End File IO		------
	//================================
	
	//================================
	//------  Begin Processing  ------
	//================================
	
	public static Node buildDecisionTree(ArrayList<Instance> examples, ArrayList<String> attributes)
	{
		Node temp = new Node();
		
		String best = bestAttribute(attributes, examples);
		temp.setAttribute(best);
		
		if(examples.size() == 1)
		{
			if(examples.get(0).getClassification() == 0)
			{
				temp.setAttribute(attributes.get(attributes.size()-1) + " = FALSE");
			}
			else
			{
				temp.setAttribute(attributes.get(attributes.size()-1) + " = TRUE");
			}
			return temp;
		}
		else if(attributes.size() == 1)
		{
			if(majorityClassification(examples) == 0)
			{
				temp.setAttribute(attributes.get(attributes.size()-1) + " = FALSE");
			}
			else
			{
				temp.setAttribute(attributes.get(attributes.size()-1) + " = TRUE");
			}
			return temp;
		}
		else if( areAllSameClassification( examples ) )
		{		
			if(examples.get(0).getClassification() == 0)
			{
				temp.setAttribute(attributes.get(attributes.size()-1) + " = FALSE");
			}
			else
			{
				temp.setAttribute(attributes.get(attributes.size()-1) + " = TRUE");
			}
			return temp;
		}
		else if( areAllSameAttribVal(examples, attributeIndex(best)))
		{
			if(majorityClassification(examples) == 0)
			{
				temp.setAttribute(attributes.get(attributes.size()-1) + " = FALSE");
			}
			else
			{
				temp.setAttribute(attributes.get(attributes.size()-1) + " = TRUE");
			}
			return temp;
		}
		else
		{
			ArrayList<Instance> noExamples = trimExamples(attributeIndex(best), 0, examples);
			ArrayList<Instance> yesExamples = trimExamples(attributeIndex(best), 1, examples);	
			
			ArrayList<String> tempAttributes = trimAttributes(attributes, best);
			
			temp.setLeftChild(buildDecisionTree(noExamples, tempAttributes));
			temp.setRightChild(buildDecisionTree(yesExamples, tempAttributes));
		}
		return temp;
	}
	
	// bestAttribute( ) - returns the best attribute based on calculated gain
	public static String bestAttribute( ArrayList<String> newAttributes, ArrayList<Instance> examples)
	{
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for(int i = 0; i < attributes.size()-1; i++)
		{
			for(int j = 0; j < newAttributes.size(); j++)
			{
				if(attributes.get(i).equals(newAttributes.get(j)))
				{
					indexes.add(i);
				}
			}
		}		
		String best = "";
		double bestGain = -1;
		for(int i = 0; i < indexes.size(); i++)
		{
			double currentGain = calcGain(indexes.get(i), examples);
			if(currentGain > bestGain)
			{
				best = attributes.get(indexes.get(i));
				bestGain = currentGain;
			}
		}
		//if(best.isEmpty()) //If there is no best attribute, return the value of the final attribute.
		//{
		//	best = attributes.get(attributes.size()-1);
		//}
		return best;
	}
	
	//Locates ew set of examples for the attribute
	public static ArrayList<Instance> trimExamples(int attribute, int value, ArrayList<Instance> examples)
	{	
		//System.out.println("trimExamples");
		ArrayList<Instance> newExamples = new ArrayList<Instance>();
		
		for(int i = 0; i < examples.size(); i++)
		{
			//System.out.println(examples.get(i).getValue( attribute )+ "=" + value);
			if(examples.get(i).getValue( attribute ) == value)
			{
				Instance instance = examples.get( i );
				newExamples.add( instance );
			}
		}
		return newExamples;
	}
	
	public static ArrayList<String> trimAttributes(ArrayList<String> attributes, String best)
	{
		ArrayList<String> newAttributes = new ArrayList<String>();
		for(int i = 0; i < attributes.size(); i++)
		{
			String currAttribute = attributes.get(i);
			if( !currAttribute.equals( best ) )
			{
				newAttributes.add( currAttribute );
			}
		}
		return newAttributes;
	}
	
	//Check if the attributes are all 0 or all 1
	public static boolean areAllSameAttribVal(ArrayList<Instance> examples, int attribute)
	{
		//System.out.println("AllSameAttrib");
		int classification = examples.get(0).getValue( attribute );
		for(int i = 1; i < examples.size(); i++)
		{
			//System.out.println(examples.get(i).getValue( attribute ) + "=" + classification);
			if(examples.get(i).getValue( attribute ) != classification)
			{
				return false;
			}
		}
		return true;
	}
	
	//Returns true if final attribute values are all 0 or all 1
	public static boolean areAllSameClassification(ArrayList<Instance> examples)
	{
		//System.out.println("AllSameClass");
		int classification = examples.get(0).getClassification();
		for(int i = 1; i < examples.size(); i++)
		{
			//System.out.println(examples.get(i).getClassification() + "=" + classification);
			if(examples.get(i).getClassification() != classification)
			{
				return false;
			}
		}
		return true;
	}
	
	//Returns the majority classification of all the examples
	public static int majorityClassification(ArrayList<Instance> examples)
	{
		int zero = 0;
		int one = 0;
		for(int i = 0; i < examples.size(); i++)
		{
			if(examples.get(i).getClassification() == 0)
			{
				zero++;
			}
			else
			{
				one++;
			}
		}
		if(zero >= one)
		{
			return 0;
		}
		return 1;
	}
	
	//Returns the index of an attribute
	public static int attributeIndex(String attribute)
	{
		for(int i = 0; i < attributes.size(); i++)
		{
			if(attributes.get(i).equals(attribute))
			{
				return i;
			}
		}
		return -1;
	}
	
	//Calculate the information 
	public static double calcI( ArrayList<Instance> examples )
	{
		double p = 0.0;
		double n = 0.0;
		for(int i = 0; i < examples.size(); i++)
		{
			if(examples.get( i ).getClassification() == 1)
			{
				p++;
			}
			else
			{
				n++;
			}
		}
		double total = p + n;
		return -(p/total)*(Math.log(p/total)/Math.log(2))-(n/total)*(Math.log(n/total)/Math.log(2));
	}
	
	//Calculates the information for a specific attribute
	public static double calcIa( int attribute, ArrayList<Instance> examples)
	{
		double p = 0.0;
		double pTrue = 0.0;
		double pFalse = 0.0;
		double n = 0.0;
		double nTrue = 0.0;
		double nFalse = 0.0;
		for(int i = 0; i < examples.size(); i++)
		{	
			if(examples.get( i ).getValue( attribute ) == 1)
			{
				
				p++;
				if(examples.get( i ).getClassification() == 1)
				{
					pTrue++;
				}
				else
				{
					pFalse++;
				}
			}
			else
			{
				n++;
				if(examples.get( i ).getClassification() == 1)
				{
					nTrue++;
				}
				else
				{
					nFalse++;
				}
			}
			
		}
				
		double total = p + n;
		double first;
		
		if(pTrue == 0 )
		{
			first = -(pFalse/p)*(Math.log(pFalse/p)/Math.log(2));
		}
		else if(pFalse == 0)
		{
			first = -(pTrue/p)*(Math.log(pTrue/p)/Math.log(2));
		}
		else
		{
			first = -(pTrue/p)*(Math.log(pTrue/p)/Math.log(2))-(pFalse/p)*(Math.log(pFalse/p)/Math.log(2));
		}
		
		double second; 
		if(nTrue == 0)
		{
			second = -(nFalse/n)*(Math.log(nFalse/n)/Math.log(2));
		}
		else if( nFalse == 0 )
		{
			second = -(nTrue/n)*(Math.log(nTrue/n)/Math.log(2));
		}
		else
		{
			second = -(nTrue/n)*(Math.log(nTrue/n)/Math.log(2))-(nFalse/n)*(Math.log(nFalse/n)/Math.log(2));
		}
		
		if(p == 0)
		{
			return (n/total) * second;
		}
		else if(n == 0)
		{
			return (p/total)* first;	
		}
		return (p/total)* first + (n/total) * second;
	}
	
	//Calculate information gain for a specific attribute
	public static double calcGain( int attribute, ArrayList<Instance> examples)
	{
		return calcI( examples ) - calcIa( attribute, examples);
	}
	
	//================================
	//------   End Processing   ------
	//================================
	
	//================================
	//------  Begin Text Output  -----
	//================================
	
	public static void displayTree(Node root)
	{
		printNode(root, "");
	}
	
	public static void printNode(Node root, String tab)
	{
		System.out.println(tab + root.getAttribute());
		
		if(root.getLeftChild() != null)
		{
			System.out.println(tab + " " + "No:");
			printNode(root.getLeftChild(), tab + "  ");
		}
		
		
		if(root.getRightChild() != null)
		{
			System.out.println(tab + " " + "Yes:");
			printNode(root.getRightChild(), tab + "  ");
		}
		
	}

	//================================
	//------  End Text Output   ------
	//================================	
}
