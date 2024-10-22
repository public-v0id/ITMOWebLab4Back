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
        List<List<String>> resList = (List<List<String>>)context.getAttribute("resList");
        if (resList == null) {
            resList = new ArrayList<List<String>>();
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
        List<List<String>> resList = (List<List<String>>)context.getAttribute("resList");
        if (resList == null) {
            resList = new ArrayList<List<String>>();
        }
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();
        String newline = "";
        while ((newline = reader.readLine()) != null) {
            builder.append(newline);
        }
        JSONObject json = new JSONObject(builder.toString());
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        String agent = json.getString("agent");
        if (agent != null) {
            HashMap<String, Long> requests = (HashMap<String, Long>)context.getAttribute("requests");
            if (requests == null) {
                requests = new HashMap<String, Long>();
            }
            requests.put(agent, (requests.containsKey(agent) ? requests.get(agent) + 1 : 1));
            logger.log(Level.INFO, "AGENT " + agent + " " + requests.get(agent));
            context.setAttribute("requests", requests);
        }
        if (json.getString("x") == null || json.getString("y") == null || json.getString("r") == null || json.getString("x").length() > 10 || json.getString("y").length() > 10 || json.getString("r").length() > 10)
        {
            logger.log(Level.SEVERE, "Not all parameters are present or some are too long!");
            return;
        }
        double x = 0, y = 0, r = 0;
        try {
            x = Double.parseDouble(json.getString("x"));
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "X is not a floating point value");
            return;
        }
        try {
            y = Double.parseDouble(json.getString("y"));
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Y is not a floating point value");
            return;
        }
        try {
            r = Double.parseDouble(json.getString("r"));
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "R is not a floating point value");
            return;
        }
        logger.log(Level.INFO, "Values: " + x + " " + y + " " + r);
        try {
            Parameters params = new Parameters(x, y, r);
            boolean res = Validator.check(params);
            String time = Instant.now().toString();
            long applTime = System.nanoTime()-startTime;
            logger.log(Level.INFO, "RESLIST SIZE " + Integer.toString(resList.size()));
            ArrayList<String> newLine = new ArrayList<String>();
            newLine.add(Parser.doubleToString(x));
            newLine.add(Parser.doubleToString(y));
            newLine.add(Parser.doubleToString(r));
            newLine.add(Boolean.toString(res));
            newLine.add(Long.toString(applTime));
            newLine.add(time);
            resList.add(newLine);
            context.setAttribute("resList", resList);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(Parser.genJSONresp(res,applTime, time));
        }
        catch (ParametersException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
    }
}
