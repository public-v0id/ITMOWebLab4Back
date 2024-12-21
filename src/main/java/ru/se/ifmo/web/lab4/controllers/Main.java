package ru.se.ifmo.web.lab4.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import ru.se.ifmo.web.lab4.beans.Point;
import ru.se.ifmo.web.lab4.beans.Results;
import ru.se.ifmo.web.lab4.beans.User;
import ru.se.ifmo.web.lab4.cores.Authentificator;
import ru.se.ifmo.web.lab4.cores.TokenUtils;
import ru.se.ifmo.web.lab4.dao.ResultsDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.se.ifmo.web.lab4.exceptions.WrongCoordinatesException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/main")
public class Main {
    @POST
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response check(@HeaderParam("Auth") String token, Point point) {
        if (token == null) {
            return Response.status(403).entity("Error! You're unauthorized!").build();
        }
        if (point == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! No point!").build();
        }
        try {
            TokenUtils.validateToken(token);
            ResultsDAO resultsDAO = new ResultsDAO();
            return Response.status(200).entity(resultsDAO.addResult(point)).build();
        }
        catch (WrongCoordinatesException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Bad coordinates!").build();
        }
        catch (RuntimeException e) {
            return Response.status(403).entity("Error! You're unauthorized!").build();
        }
    }
    @GET
    @Path("/getTable")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTable(@HeaderParam("Auth") String token, @QueryParam("login") String login) {
        if (token == null) {
            return Response.status(403).entity("Error! You're unauthorized!").build();
        }
        if (login == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! No login!").build();
        }
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        try {
            TokenUtils.validateToken(token);
            ResultsDAO resultsDAO = new ResultsDAO();
            return Response.status(200).entity(resultsDAO.getAllResults(login)).build();
        }
        catch (RuntimeException e) {
            return Response.status(403).entity("Error! You're unauthorized!").build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Something went wrong!").build();
        }
    }
}
