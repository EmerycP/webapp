package com.emerycprimeau.tools;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GestionExceptions implements ExceptionMapper<Exception> {

    public Response toResponse(Exception e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getClass().getSimpleName()).build();
    }
}
