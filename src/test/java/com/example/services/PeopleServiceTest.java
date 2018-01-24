package com.example.services;

import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue; 
import org.junit.After; 
import org.junit.Before; 
import org.junit.BeforeClass; 
import org.junit.Ignore; 
import org.junit.Test;

import com.example.exceptions.EmailFormatNotCorrectException;
import com.example.exceptions.PersonAlreadyExistsException;
import com.example.exceptions.PersonNotFoundException;
import com.example.resource.Person; 


public class PeopleServiceTest {
	
  private static PeopleService peopleService;
	
 @BeforeClass 
  public static void createInstance() { 
	 peopleService = new PeopleService(); 
   }
 

@Before 
public void beforeEachTest() {
	//clean up
	 peopleService = new PeopleService(); 
 } 

 
 @Test 
 public void testGetAllPeople() { 
     int result = peopleService.getAllPeople().size();
     assertEquals(4, result); 
 }
 
 @Test 
 public void testAddPerson() { 
	 peopleService.addPerson("test@gmail.com", "test", "test");
     int result = peopleService.getAllPeople().size();
     assertEquals(5, result); 
 }
 
 @Test 
 public void testGetByEmail() { 
	 Person person = peopleService.getByEmail("admin@gmail.com");
     assertNotNull(person); 
 }
 
 @Test 
 public void testRemovePerson() { 
	 peopleService.removePerson("admin@gmail.com");
	 int result = peopleService.getAllPeople().size();
     assertEquals(3, result);  
 }

  
 @Test(expected=EmailFormatNotCorrectException.class)
 public void testGetByEmailFail() { 
	 Person person = peopleService.getByEmail("admin");
     assertNull(person); 
 }
 
 @Test(expected=PersonNotFoundException.class)
 public void testGetByEmailNotFoundFail() { 
	 Person person = peopleService.getByEmail("admin1@gmail.com");
     assertNull(person); 
 }
 
 @Test(expected=EmailFormatNotCorrectException.class)
 public void testAddPersonFail() { 
	 peopleService.addPerson("test", "test", "test");
     int result = peopleService.getAllPeople().size();
     assertEquals(4, result); 
 }
 
 @Test(expected=PersonAlreadyExistsException.class)
 public void testAddPersonFailAlreadyExist() { 
	 Person person = peopleService.addPerson("admin@gmail.com", "test", "test");
	 assertNull(person); 
 }
 
 @Test(expected=EmailFormatNotCorrectException.class)
 public void testRemovePersonFail() { 
	 peopleService.removePerson("admin");
	 int result = peopleService.getAllPeople().size();
     assertEquals(4, result);  
 }
 
 @Test(expected=PersonNotFoundException.class) 
 public void testRemovePersonFailNotFound() { 
	 peopleService.removePerson("admin1@gmail.com");
	 int result = peopleService.getAllPeople().size();
     assertEquals(4, result);  
 }

 
 
 
 
 
 
 

	

}

