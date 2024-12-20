package ru.se.ifmo.web.lab4.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import ru.se.ifmo.web.lab4.beans.Point;
import ru.se.ifmo.web.lab4.beans.Results;
import ru.se.ifmo.web.lab4.beans.User;
import ru.se.ifmo.web.lab4.cores.Authentificator;
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
    public Response check(Point point) {
        if (point == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! No point!").build();
        }
        try {
            ResultsDAO resultsDAO = new ResultsDAO();
            return Response.status(200).entity(resultsDAO.addResult(point)).build();
        }
        catch (WrongCoordinatesException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Bad coordinates!").build();
        }
    }
    @GET
    @Path("/getTable")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTable(@QueryParam("login") String login) {
        if (login == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! No login!").build();
        }
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        try {
            ResultsDAO resultsDAO = new ResultsDAO();
            return Response.status(200).entity(resultsDAO.getAllResults(login)).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error! Something went wrong!").build();
        }
    }
}
