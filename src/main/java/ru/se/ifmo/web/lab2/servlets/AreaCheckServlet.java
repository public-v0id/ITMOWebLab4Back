package ru.se.ifmo.web.lab2.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.se.ifmo.web.lab2.classes.*;
import ru.se.ifmo.web.lab2.cores.*;
import ru.se.ifmo.web.lab2.exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AreaCheckServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ServletContext context = getServletContext();
        List<DTO> resList = (List<DTO>)context.getAttribute("resList");
        if (resList == null) {
            resList = new ArrayList<DTO>();
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("second.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        HashMap<String, Long> requests = (HashMap<String, Long>)context.getAttribute("requests");
        if (requests == null) {
            requests = new HashMap<String, Long>();
        }
        JSONArray arr = new JSONArray();
        arr.put(requests);
        JSONObject obj = new JSONObject();
        obj.put("values", arr);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().write(obj.toString());
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        long startTime = System.nanoTime();
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.log(Level.INFO, "Got POST request");
        List<DTO> resList = (List<DTO>)context.getAttribute("resList");
        if (resList == null) {
            resList = new ArrayList<DTO>();
        }
        try {
            Parameters params = Parser.JSONtoParams(req);
            logger.log(Level.INFO, "params are " + params.getX() + " " + params.getY() + " " + params.getR());
            DTO res = Parser.ParamsToDTO(params, startTime);
            logger.log(Level.INFO, "res is " + res.getRes());
            resList.add(res);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(Parser.genJSONresp(res));
        } catch (JSONConvException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
    }
}
