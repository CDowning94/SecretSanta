/**
 * Christopher Downing
 * September 4, 2020
 * 
 * Secret Santa
 * This program takes a list of names in a .txt file, called "names.txt",
 * and creates a Secret Santa game using those people, so that each person
 * is buying for exactly one other person, and no one is buying their own name.
 */

import java.util.*;
import java.io.*;

class Person
{
	private String _name = null;
	private String _recipient = null;
	private String _email = null;
	
	Person(String name, String recipent, String email)
	{
		_name = name;
		_recipient = recipent;
		_email = email;
	}
	
	Person(String name, String recipient)
	{
		this(name,  recipient, null);
	}
	
	Person(String name)
	{
		this(name, null, null);
	}
	
	public String getName() { return _name; }
	public String getRecipient() { return _recipient; }
	public void setRecipient(String recipient) { _recipient = recipient; }
	
	
	public String getEmail() { return _email; }
	public String getFormatted()
	{
		String format = _name + " is buying for " + _recipient + ".";
		return format;
	}
}


public class SecretSanta
{
	public static void main(String args[])
	{	
		String filename = "names.txt";
		ArrayList<Person> list = new ArrayList<Person>();
		
		BufferedReader in;
		try
		{
			in = new BufferedReader(new FileReader(filename));
			
			String line = in.readLine();
			while(line != null)
			{
				list.add(new Person(line));
				line = in.readLine();
			}
			
			in.close();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		
		if(list.size() <= 1)
		{
			System.out.println("There must be 2 or more names in names.txt!");
			System.exit(0);
		}
		
		arrangeSecrets(list);
		
		for(Person p : list)
		{
			System.out.printf("%s\n", p.getFormatted());
		}
	}
	
	// Assigns a recipient to every single person in the list, ensuring no one draws their own name
	public static void arrangeSecrets(ArrayList<Person> list)
	{
		ArrayList<String> secrets = new ArrayList<String>();
		Random rand = new Random();
		
		boolean good = false;
		while(!good)
		{
			good = true;
			
			// Copy the list of names
			for(Person p : list)
			{
				secrets.add(p.getName());
			}
			
			// Have everyone "draw" a name from the list, and then remove that name
			for(Person p : list)
			{
				int secretIndex = rand.nextInt(secrets.size());
				p.setRecipient(secrets.get(secretIndex));
				secrets.remove(secretIndex);
			}
			
			// Check to make sure that no one drew their own name
			for(Person p : list)
			{
				if(p.getName().equals(p.getRecipient()))
					good = false;
			}
		}
	}
}