package ru.se.ifmo.web.lab4.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import ru.se.ifmo.web.lab4.beans.User;
import ru.se.ifmo.web.lab4.cores.Authentificator;
import ru.se.ifmo.web.lab4.exceptions.UnknownLoginException;
import ru.se.ifmo.web.lab4.exceptions.UserExistsException;
import ru.se.ifmo.web.lab4.exceptions.WrongPasswordException;

import java.lang.ref.WeakReference;

@Path("/index")
public class Index {
    @GET
    @Path("/signIn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(@QueryParam("login") String login, @QueryParam("password") String password) {
        if (login == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Missing login or password!").build();
        }
        try {
            return Response.status(200).entity(Authentificator.signIn(new User(login, password))).build();
        }
        catch (UnknownLoginException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error! User with such login not found!").build();
        }
        catch (WrongPasswordException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error! Wrong password!").build();
        }
        catch (WebApplicationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Something went wrong!").build();
        }
    }
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Missing user data!").build();
        }
        try {
            return Response.status(200).entity(Authentificator.register(user)).build();
        }
        catch (UserExistsException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error! User with such login already exists!").build();
        }
        catch (WebApplicationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Something went wrong!").build();
        }
    }
}
