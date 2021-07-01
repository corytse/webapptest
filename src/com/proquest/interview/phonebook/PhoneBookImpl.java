package com.proquest.interview.phonebook;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public List people;
	
	@Override
	public void addPerson(Person newPerson) {
		//TODO: write this method
		String sqlInsert = "INSERT INTO PHONEBOOK(NAME,PHONENUMBER,ADDRESS) VALUES(?,?,?)";
		try {
			PreparedStatement st = DatabaseUtil.getConnection().prepareStatement(sqlInsert);
			st.setString(1, newPerson.name);
			st.setString(2, newPerson.phoneNumber);
			st.setString(3, newPerson.address);
			st.executeUpdate();
			DatabaseUtil.getConnection().commit();
			DatabaseUtil.getConnection().close();			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Person> findPerson(String firstName, String lastName) {
		//TODO: write this method
		String sqlsel = "SELECT NAME,PHONENUMBER,ADDRESS FROM PHONEBOOK WHERE NAME = '";
		sqlsel = sqlsel + firstName + " " + lastName;
		sqlsel = sqlsel +"'";
		
	    List<Person> personList= new ArrayList<Person>();

		java.sql.Statement st;
		try {
			st = DatabaseUtil.getConnection().createStatement();
			ResultSet rs = st.executeQuery(sqlsel);

			while(rs.next()){
			    //Retrieve by column name
			     String nameStr  = rs.getString("NAME");
			     String phoneNumberStr = rs.getString("PHONENUMBER");
			     String addressStr = rs.getString("ADDRESS");
			     

			    //Display values
			    //System.out.print(nameStr + "|"+ phoneNumberStr + "|"+ addressStr + "\n");
			    
			    Person resultPerson = new Person(); 
			    resultPerson.name = nameStr;
			    resultPerson.phoneNumber = phoneNumberStr;
			    resultPerson.address = addressStr;
			    personList.add(resultPerson);
			
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return personList;
		
	}
	
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database

		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/ 
		// TODO: print the phone book out to System.out
		// TODO: find Cynthia Smith and print out just her entry
		// TODO: insert the new person objects into the database
		
		Person johnSmith = new Person();
		johnSmith.name = "John Smith";
		johnSmith.phoneNumber ="(248) 123-4567";
		johnSmith.address = "1234 Sand Hill Dr, Royal Oak, MI";
		
		Person cynthiaSmith = new Person();
		cynthiaSmith.name = "Cynthia Smith";
		cynthiaSmith.phoneNumber = "(824) 128-8758";
		cynthiaSmith.address = "875 Main St, Ann Arbor, MI";
		
		PhoneBook phoneBook = new PhoneBookImpl();
		phoneBook.addPerson(johnSmith);
		phoneBook.addPerson(cynthiaSmith);
		
		System.out.println("Print all result set");
		printAll();
		
		System.out.println("\n");
		
		System.out.println("Print find person result");
		List<Person> findPersonList = phoneBook.findPerson("Cynthia", "Smith");
		for(Person person: findPersonList) {
			System.out.print(person.name + "|"+ person.phoneNumber + "|"+ person.address + "\n");
		}
		
		//phoneBook.addPerson(newPerson);
		
		
	}

private static void printAll() {
	String sqlsel = "SELECT NAME,PHONENUMBER,ADDRESS FROM PHONEBOOK";
	java.sql.Statement st;
	try {
		st = DatabaseUtil.getConnection().createStatement();
		ResultSet rs = st.executeQuery(sqlsel);

		while(rs.next()){
		    //Retrieve by column name
		     String nameStr  = rs.getString("NAME");
		     String phoneNumberStr = rs.getString("PHONENUMBER");
		     String addressStr = rs.getString("ADDRESS");
		     

		    //Display values
		    System.out.print(nameStr + "|"+ phoneNumberStr + "|"+ addressStr + "\n");

		 }
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
