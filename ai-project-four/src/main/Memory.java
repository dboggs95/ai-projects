package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Memory
{
	private int[][] board;
	
	private static ArrayList<Integer> s = new ArrayList<Integer>();
	private static ArrayList<Integer> a = new ArrayList<Integer>();
	
	public Memory()
	{
		board = new int[][] 
		{
			{1,-1,-1,0,0,0,0,0,0,0},
			{-1,1,-1,0,0,0,0,0,0,0},
			{-1,-1,1,0,0,0,0,0,0,0}
		};
		
		Scanner scanner = null;
		
		File memoryFile = new File("Memory.txt");
		if(memoryFile.exists())
		{
			try
			{
				scanner = new Scanner(memoryFile);
			} 
			catch(FileNotFoundException e1)
			{
				System.out.println("File was not found.");
				e1.printStackTrace();
			}
			finally
			{
				String num = "";
				ArrayList<Integer> memoryInts = new ArrayList<Integer>();
				while(scanner.hasNext())
				{
					String temp = scanner.next();
					num = temp;
					memoryInts.add(Integer.parseInt(num));
					num = "";
				}
				scanner.close();
				int index = 0;
				for(int a = 3; a > 0; a--)
				{
					for(int s = 1; s <= 10; s++ )
					{
						this.setQ(s, a, memoryInts.get(index));
						index++;
					}
				}
			}
		}
		s.ensureCapacity(10);
		a.ensureCapacity(10);
	}
	private void setQ(int s, int a, int qVal)
	{
		this.board[a-1][s-1] = qVal;
	}
	
	public int getQ(int s, int a)
	{
		return this.board[a-1][s-1];
	}
	public void recordMove(int sVal, int aVal)
	{
		s.add(sVal);
		a.add(aVal);
	}
	
	public void saveMemory(boolean NIMWon)
	{
		if(NIMWon)
		{
			for(int i = 0; i < s.size(); i++)
			{
				int temp = this.getQ(s.get(i), a.get(i));
				this.setQ(s.get(i), a.get(i), temp+1);
			}
		}
		else
		{
			for(int i = 0; i < s.size(); i++)
			{
				int temp = this.getQ(s.get(i), a.get(i));
				this.setQ(s.get(i), a.get(i), temp-1);
			}
		}
		
		try
		{
		    PrintWriter writer = new PrintWriter("Memory.txt", "UTF-8");
		    for(int a = 3; a > 0; a--)
		    {
		    	String temp = "";
		    	for(int s = 1; s <= 10; s++)
		    	{
		    		temp += this.getQ(s, a);
		    		if(s != 10)
		    		{
		    			temp += " ";
		    		}
		    	}
		    	writer.println(temp);
		    }
		    writer.close();
		} 
		catch (IOException e) 
		{
		   System.out.println("Error: Cannot save memory.");
		}
	}
	public String toString()
	{
		String temp = "";
		temp += "Memory:";
		temp += System.lineSeparator();
		temp += "==============================";
		temp += System.lineSeparator();
		for(int a = 2; a >= 0; a--)
		{
			for(int s = 0; s < 10; s++)
			{
				if(board[a][s] > 0)
				{
					temp += "+";
				}
				else if(board[a][s] == 0)
				{
					temp += " ";
				}
				temp += board[a][s] + " ";			
			}
			temp += System.lineSeparator();
		}
		temp += "==============================";
		return temp;
	}
}
