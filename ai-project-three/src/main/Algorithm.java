package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Algorithm
{
	private Map<Integer, List<Cell>> pointGroups = new HashMap<>();
	private Board currentBoard = null;
	public Algorithm(Board newBoard)
	{
		this.currentBoard = newBoard;
	}
	
	public void runAlgorithm()
	{
		initializeMap();
		if (solve(new Cell(0, 0))) 
		{
            System.out.println("Failure: Puzzle unsolvable!");
        }
		//NOTE: This check cannot occur outside of solve().
		// For some reason it deletes the solution from the
		// object at the end of the function.
		//System.out.println(currentBoard.toString());
		//currentBoard.isSolved();
	}
	
    private void initializeMap() 
    {
        for (int i = 1; i <= 9; ++i) 
        {
            pointGroups.put(i, new ArrayList<Cell>());
        }

        for (int i = 0; i < 3; ++i) 
        {
            for (int j = 0; j < 3; ++j) 
            {
                pointGroups.get(1).add(new Cell(i, j));
            }

            for (int j = 3; j < 6; ++j) 
            {
                pointGroups.get(2).add(new Cell(i, j));
            }

            for (int j = 6; j < 9; ++j) 
            {
                pointGroups.get(3).add(new Cell(i, j));
            }
        }

        for (int i = 3; i < 6; ++i) {
            for (int j = 0; j < 3; ++j) {
                pointGroups.get(4).add(new Cell(i, j));
            }

            for (int j = 3; j < 6; ++j) {
                pointGroups.get(5).add(new Cell(i, j));
            }

            for (int j = 6; j < 9; ++j) {
                pointGroups.get(6).add(new Cell(i, j));
            }
        }

        for (int i = 6; i < 9; ++i) {
            for (int j = 0; j < 3; ++j) {
                pointGroups.get(7).add(new Cell(i, j));
            }
            for (int j = 3; j < 6; ++j) {
                pointGroups.get(8).add(new Cell(i, j));
            }
            for (int j = 6; j < 9; ++j) {
                pointGroups.get(9).add(new Cell(i, j));
            }
        }
    }
	
	//Check to make sure new cell value is unique to row/column/square.
	private boolean uniqueInGroup(Cell cell) 
	{
        int groupNumber = -1;
        for (int i = 1; i <= 9; ++i) 
        {
            List<Cell> list = pointGroups.get(i);
            if (list.contains(cell)) 
            {
            	groupNumber = i;
            }
        }

        List<Cell> list = pointGroups.get(groupNumber);
        for (int i = 0; i < 9; ++i) 
        {
            if (list.get(i).equals(cell)) 
            {
                continue;
            }
            if (currentBoard.getBoard()[list.get(i).getX()][list.get(i).getY()] == currentBoard.getBoard()[cell.getX()][cell.getY()] && currentBoard.getBoard()[cell.getX()][cell.getY()] != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isAlreadyPlaced(int number, int row, int column) 
    {
        //Check column
        for (int i = 0; i < 9; ++i) 
        {
            if (number == currentBoard.getBoard()[row][i] || number == currentBoard.getBoard()[i][column]) 
            {
                return true; //duplicate exists
            }
        }
        //Cell cell = new Cell(row, column); ???

        return false; //Unique
    }

    
    public boolean checkLoss(Cell cell) 
    {
        for (int i = 1; i <= 9; ++i) 
        {
            if (!isAlreadyPlaced(i, cell.getX(), cell.getY())) {
                return false; //can be placed
            }
        }
        return true; //lost
    }

    private boolean place(Cell cell, int number) 
    {
        if (!isAlreadyPlaced(number, cell.getX(), cell.getY())) 
        {
            currentBoard.setBoardCell(cell.getX(), cell.getY(), number);
            return true;    //placed
        }
        return false;   //coudln't place
    }

    private Cell getNextCell(Cell previousCell) 
    {
        Cell cell = new Cell(previousCell.getX(), previousCell.getY());

        /*if (previousCell.getX() == 8 && previousCell.getY() == 8) 
        {
            return null;
        }*/
        if (previousCell.getY() == 8) 
        {
            cell.setY(0);
            cell.setX(cell.getX()+1);
        } else 
        {
            cell.setY(cell.getY()+1);
        }
        return cell;
    }

    private boolean solve(Cell cell) {

        if (currentBoard.getBoard()[cell.getX()][cell.getY()] != 0) 
        {
            if (cell.getX() == 8 && cell.getY() == 8) 
            {
                System.out.println(currentBoard.toString());
                currentBoard.isSolved();
                return true;
            }
            Cell nextCell = getNextCell(cell);
            return solve(nextCell);

        }

        for (int i = 1; i <= 9; ++i) 
        {
            if (checkLoss(cell)) 
            { //Nothing can be placed
            	break;
            }
            boolean placed = place(cell, i);

            /*if (!placed) 
            {
            	
            }*/

            if (placed) 
            { //placed
                if (!uniqueInGroup(cell)) 
                {
                    continue;
                }
                if (cell.getX() == 8 && cell.getY() == 8) 
                {
                	System.out.println(currentBoard.toString());
                	currentBoard.isSolved();
                	return true;
                }
                Cell nextCell = getNextCell(cell);
                solve(nextCell);
            }
        }

        //Board Failure - Shoud'nt be an issue
        currentBoard.getBoard()[cell.getX()][cell.getY()] = 0;
        return false; //Did not work well
    }
}


