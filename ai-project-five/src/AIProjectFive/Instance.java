package AIProjectFive;

import java.util.ArrayList;

/*
 * Instance objects carry a list of attributes and the corresponding
 * values representing true and false 
 */

public class Instance {
	
	private ArrayList<String> attributes;
	private ArrayList<Integer> values;	
	public Instance( ArrayList<String> attributes, ArrayList<Integer> values )
	{
		this.attributes = attributes;
		this.values = values;
	}
	
	//Sets attribute list
	public void setAttributes(ArrayList<String> attributes)
	{
		this.attributes = attributes;
	}
	
	//Returns the attributes list
	public ArrayList<String> getAttributes()
	{
		return attributes;
	}
	
	//Returns an attribute at a specific index
	public String getAttribute(int i)
	{
		return attributes.get(i);
	}
	
	public String getFinalAttribute()
	{
		return attributes.get(attributes.size()-1);
	}
	
	//Finds the value of a specific attribute
	public int findAttributeValue( String attribute )
	{
		int value = -1;
		for(int i = 0; i < attributes.size()-1; i++)
		{
			if(attributes.get(i).equals(attribute))
			{
				value = values.get(i);
			}
		}
		return value;
	}
	
	//Sets all the values 
	public void setValues( ArrayList<Integer> values )
	{
		this.values = values;
	}
	
	//Returns arraylist values
	public ArrayList<Integer> getValues( )
	{
		return values;
	}
	
	//Value at a specific index
	public int getValue( int i )
	{
		return values.get(i);
	}
	 
	public int getClassification( )
	{
		if(values.size() > 0)
			return values.get(values.size()-1);
		return -1;
	}
	
	//Return label for instance
	public String getLabel( String attribute )
	{
		//System.out.println("Function: Instance::getLabel");
		//System.out.println("Input: attribute = " + attribute);
		for(int i = 0; i < attributes.size(); i++)
		{
			if(attributes.get(i).equals( attribute ))
			{
				if( values.get(i) == 1)
				{
					//System.out.println("Output: Yes");
					//System.out.println(System.lineSeparator());
					return "Yes";
				}
				//System.out.println("Output: No");
				//System.out.println(System.lineSeparator());
				return "No";
			}
		}
		//System.out.println("Output: none");
		//System.out.println(System.lineSeparator());
		return "none";
	}
	
}
