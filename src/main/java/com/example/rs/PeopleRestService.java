package com.example.rs;

import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.wso2.msf4j.Microservice;
import com.example.resource.Person;
import com.example.services.PeopleService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;


@Path( "/people" ) 
@Api( value = "/people", description = "Manage people" )
public class PeopleRestService implements  Microservice {
	Logger logger = Logger.getLogger(PeopleRestService.class);
	private PeopleService peopleService = new PeopleService();
	
	@Produces( { MediaType.APPLICATION_JSON } )
	@Path( "/getAllPeople" )
	@GET
	@ApiOperation( value = "List all people", notes = "List all people", response = Person.class, responseContainer = "List")
	public Collection< Person > getAllPeople() {
		return peopleService.getAllPeople();
	}

	@Produces( { MediaType.APPLICATION_JSON } )
	@Path( "/getPeople/{email}" )
	@GET
	@ApiOperation( value = "Find person by e-mail", notes = "Find person by e-mail", response = Person.class )
	@ApiResponses( {
	    @ApiResponse( code = 404, message = "Person with such e-mail doesn't exists" )			 
	} )
	public Person getPeople( @ApiParam( value = "E-Mail address to lookup for", required = true ) @PathParam( "email" ) final String email ) {
		return peopleService.getByEmail( email );
	}

	@Produces( { MediaType.APPLICATION_JSON  } )
	@Path( "/addPerson" )
	@POST
	@ApiOperation( value = "Create new person", notes = "Create new person" )
	@ApiResponses( {
	    @ApiResponse( code = 201, message = "Person created successfully" ),
	    @ApiResponse( code = 409, message = "Person with such e-mail already exists" )
	} )
	public Person addPerson( 
			@ApiParam( value = "E-Mail", required = true ) @FormParam( "email" ) final String email, 
			@ApiParam( value = "First Name", required = true ) @FormParam( "firstName" ) final String firstName, 
			@ApiParam( value = "Last Name", required = true ) @FormParam( "lastName" ) final String lastName ) {
		logger.debug("addPerson calling");
		Person person = peopleService.addPerson( email, firstName, lastName );
		return person;
	}
	
	@Produces( { MediaType.APPLICATION_JSON  } )
	@Path( "/updatePerson/{email}" )
	@PUT
	@ApiOperation( value = "Update existing person", notes = "Update existing person", response = Person.class )
	@ApiResponses( {
	    @ApiResponse( code = 404, message = "Person with such e-mail doesn't exists" )			 
	} )
	public Person updatePerson(			
			@ApiParam( value = "E-Mail", required = true ) @PathParam( "email" ) final String email, 
			@ApiParam( value = "First Name", required = false ) @FormParam( "firstName" ) final String firstName, 
			@ApiParam( value = "First Name", required = false ) @FormParam( "lastName" ) final String lastName ) {
		
		logger.info("updatePerson calling");
		
		final Person person = peopleService.getByEmail( email );
		
		if( firstName != null ) {
			person.setFirstName( firstName );
		}
		
		if( lastName != null ) {
			person.setLastName( lastName );
		}

		return person; 				
	}
	
	@Path( "/deletePerson/{email}" )
	@DELETE
	@ApiOperation( value = "Delete existing person", notes = "Delete existing person", response = Person.class )
	@ApiResponses( {
	    @ApiResponse( code = 404, message = "Person with such e-mail doesn't exists" )			 
	} )
	public Response deletePerson( @ApiParam( value = "E-Mail", required = true ) @PathParam( "email" ) final String email ) {
		peopleService.removePerson( email );
		return Response.ok().build();
	}

}
