package AIProjectFive;

import java.util.ArrayList;

/*
 * Node objects represents a single node in the decision tree. 
 */

public class Node {

	private String attribute;
	private Node leftChild = null; //no
	private Node rightChild = null; //yes
	
	public Node() 
	{
		
	}
	
	public Node(String attribute)
	{
		this.attribute = attribute;
	}
	
	//Set the node's attribute
	public void setAttribute(String s)
	{
		attribute = s;
	}
	
	//Get Node attribute
	public String getAttribute( )
	{
		return attribute;
	}
	
	//Sets the Node's children
	public void setLeftChild(Node lChild)
	{
		this.leftChild = lChild; //no
	}
	
	//Sets the Node's children
	public void setRightChild(Node rChild)
	{
		this.rightChild = rChild; //yes
	}
	
	//Returns the child nodes
	public Node getLeftChild( )
	{
		return leftChild;
	}
	
	//Returns the child nodes
	public Node getRightChild( )
	{
		return rightChild;
	}
}
