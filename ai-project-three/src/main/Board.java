package main;

import java.util.ArrayList;

public class Board
{
	private Integer[][] board;

	public Board(ArrayList<Integer> boardInts)
	{
		board = new Integer[][] 
		{
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
		};
		int index = 0;
		for(int j = 0; j < 9; j++)
		{
			for(int i = 0; i < 9; i++)
			{
				this.board[i][j] = boardInts.get(index);
				index++;
			}
		}
	}
	
	public Integer[][] getBoard()
	{
		return board;
	}
	
	public void setBoardCell(int x, int y, int val)
	{
		this.board[x][y] = val;
	}
	
	public boolean isFull()
	{
		for(int j = 0; j < 9; j++)
		{
			for(int i = 0; i < 9; i++)
			{
				if(board[i][j] == 0)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void isSolved()
	{
		if(!this.isFull())
		{
			System.out.println("Failure! Not all cells full.");
			return;
		}
		for(int j = 0; j < 9; j++)
		{
			int rowSum = 0;
			for(int i = 0; i < 9; i++)
			{
				rowSum += board[i][j];
			}
			if(rowSum != 45)
			{
				System.out.println("Failure! Row sum = " + rowSum + " at row " + j);
				return;
			}
		}
		for(int i = 0; i < 9; i++)
		{
			int columnSum = 0;
			for(int j = 0; j < 9; j++)
			{
				columnSum += board[i][j];
			}
			if(columnSum != 45)
			{
				System.out.println("Failure! Column sum = " + columnSum);
				return;
			}
		}
		
		System.out.println("Solution is correct!");
	}
	
	public String toString()
	{
		String temp = "";
		temp += "Current Board:";
		temp += System.lineSeparator();
		temp += "==================";
		temp += System.lineSeparator();
		for(int j = 0; j < 9; j++)
		{
			temp += "|| ";
			for(int i = 0; i < 9; i++)
			{
				if(board[i][j] != 0)
				{
					temp += board[i][j];
				}
				else
				{
					temp += " ";
				}
				if((i+1) % 3 != 0)
				{
					temp += " | ";
				}
				else
				{
					temp += " || ";
				}
				
			}
			temp += System.lineSeparator();
			if((j+1) % 3 != 0)
			{
				temp += "-----------------------------------------";
			}
			else
			{
				temp += "=========================================";
			}
			temp += System.lineSeparator();
		}
		
		return temp;
	}
}
