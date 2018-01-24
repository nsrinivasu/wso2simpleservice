package com.example.services;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import com.example.exceptions.EmailFormatNotCorrectException;
import com.example.exceptions.PersonAlreadyExistsException;
import com.example.exceptions.PersonNotFoundException;
import com.example.resource.Person;

public class PeopleService {
	Logger logger = Logger.getLogger(PeopleService.class);
	
	private final ConcurrentMap< String, Person > persons = new ConcurrentHashMap< String, Person >(); 
	
	public PeopleService() {
		//load default list of people first
		loadDefaultPeopleList();			
	}
	
	private void loadDefaultPeopleList() {
		addPerson("admin@gmail.com", "admin", "admin");
		addPerson("user1@gmail.com", "user1", "user1");
		addPerson("user2@gmail.com", "user2", "user2");
		addPerson("user3@gmail.com", "user3", "user3");				
	}

	public Collection< Person > getAllPeople() {
		logger.debug("Returning all people, size =" + persons.size());
		return persons.values();
	}
	
	public Person getByEmail( final String email ) {
		logger.info("Email : " + email);
		boolean valid = isValidEmailAddress(email);
		if(!valid) {
			throw new EmailFormatNotCorrectException(email);
		}
		final Person person = persons.get( email );
		
		if( person == null ) {
			throw new PersonNotFoundException( email );
		}
		
		return person;
	}

	public Person addPerson( final String email, final String firstName, final String lastName ) {
		boolean valid = isValidEmailAddress(email);
		if(!valid) {
			throw new EmailFormatNotCorrectException(email);
		}		
		final Person person = new Person( email );
		person.setFirstName( firstName );
		person.setLastName( lastName );
				
		if( persons.putIfAbsent( email, person ) != null ) {
			throw new PersonAlreadyExistsException( email );
		}
				
		return person;
	}
	
	public void removePerson( final String email ) {
		boolean valid = isValidEmailAddress(email);
		if(!valid) {
			throw new EmailFormatNotCorrectException(email);
		}
		if( persons.remove( email ) == null ) {
			throw new PersonNotFoundException( email );
		}
	}
	
	private boolean isValidEmailAddress(String email) {
		   return EmailValidator.getInstance().isValid(email);
		}
}
