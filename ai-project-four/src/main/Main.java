package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Main 
{
	public static int numSticksLeft;
	
	public static Memory memory = new Memory();
	
	//alternates, s and a
	public static ArrayList<Integer> moves = new ArrayList<Integer>();
	
	public static void takeSticks(int t)
	{
		if(t <= 3 && t > 0)
		{
			if(numSticksLeft < t)
			{
				numSticksLeft = 0;
			}
			else
			{
				numSticksLeft -= t;
			}
		}
		else if(t > 3)
		{
			numSticksLeft -= 3;
		}
		else
		{
			numSticksLeft -= 1;
		}
	}
	
	public static void printSticks()
	{
		for(int i = 0; i < numSticksLeft; i++)
		{
			System.out.print("| ");
		}
		System.out.print(" " + numSticksLeft);
		System.out.print(System.lineSeparator());
		System.out.print(System.lineSeparator());
	}
	
	public static void loadMemory()
	{
	}
	
	public static int takeTurn()
	{
		int a = 1;
		int score = memory.getQ(numSticksLeft, a);
		for(int i = 1; i < 3; i++)
		{
			int temp = memory.getQ(numSticksLeft, i+1);
			if( temp > score)
			{
				score = temp;
				a = i + 1;
			}
		}
		System.out.println("I'll take " + a + "!");
		memory.recordMove(numSticksLeft, a);
		return a;
	}
	
	public static void main(String[] args)
	{
		System.out.println("Hello, my name is Invader NIM! We shall play a friendly game of NIM.");
		System.out.println("The rules are simple.");
		System.out.println("Rule #1: I go first.");
		System.out.println("Rule #2: Take 1, 2, or 3 sticks each turn.");
		System.out.println("Rule #3: Whoever draws the last stick wins.");
		System.out.println("Winner gets the loser's planet! No pressure!" + System.lineSeparator());
		numSticksLeft = 10;
		loadMemory();
		printSticks();
		takeSticks(takeTurn());
		printSticks();
		//System.out.println(memory.toString());
		boolean yourTurn = true;
		Scanner scanner = null;
		while(numSticksLeft != 0)
		{
			if(yourTurn)
			{
				scanner = new Scanner(System.in);
				System.out.print("Enter the number of sticks you wish to take (1, 2, or 3): ");
				int numToTake = scanner.nextInt(); 
				if(numToTake <= 3 && numToTake > 0)
				{
					System.out.println("You take " + numToTake + ".");
				}
				else if(numToTake > 3)
				{
					System.out.println("You take " + 3 + ".");
				}
				else
				{
					System.out.println("You take " + 1 + ".");
				}
				
				takeSticks(numToTake);
				yourTurn = false;
				printSticks();
				if(numSticksLeft == 0)
				{
					System.out.println("You cheated! I want a rematch! We must play again, or else your planet is DOOOOOOMED!");
				}
			}
			else
			{
				takeSticks(takeTurn());
				yourTurn = true;
				printSticks();
				if(numSticksLeft == 0)
				{
					System.out.println("Ha! Ha! You LOOOOOSE! Your planet is mine HUMAN!");
				}
			}
		}
		scanner.close();
		memory.saveMemory(yourTurn);
		//System.out.println(memory.toString());
	}
}
