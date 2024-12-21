package ru.se.ifmo.web.lab4.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import ru.se.ifmo.web.lab4.beans.User;
import ru.se.ifmo.web.lab4.cores.Authentificator;
import ru.se.ifmo.web.lab4.cores.TokenUtils;
import ru.se.ifmo.web.lab4.exceptions.UnknownLoginException;
import ru.se.ifmo.web.lab4.exceptions.UserExistsException;
import ru.se.ifmo.web.lab4.exceptions.WrongPasswordException;

import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/index")
public class Index {
    @POST
    @Path("/signIn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(User user) {
        if (user == null || user.getLogin() == "" || user.getPassword() == "") {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Missing login or password!").build();
        }
        try {
            Authentificator.signIn(user);
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            String token = TokenUtils.generateUserToken(user);
            logger.log(Level.INFO, token);
            return Response.status(200).entity(token).build();
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
        if (user == null || user.getLogin() == "" || user.getPassword() == "") {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Missing user data!").build();
        }
        try {
            Authentificator.register(user);
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            String token = TokenUtils.generateUserToken(user);
            logger.log(Level.INFO, token);
            return Response.status(200).entity(token).build();
        }
        catch (UserExistsException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Error! User with such login already exists!").build();
        }
        catch (WebApplicationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Something went wrong!").build();
        }
    }
}
