package com.example.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class EmailFormatNotCorrectException extends WebApplicationException {
	private static final long serialVersionUID = 1L;
	public EmailFormatNotCorrectException( final String email ) {
		super(
				Response
					.status( Status.EXPECTATION_FAILED )
					.build()
			);
	}
}
