package ru.se.ifmo.web.lab2.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import ru.se.ifmo.web.lab2.classes.*;
import ru.se.ifmo.web.lab2.cores.*;
import ru.se.ifmo.web.lab2.exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AreaCheckServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.nanoTime();
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();
        String newline = "";
        while ((newline = reader.readLine()) != null) {
            builder.append(newline);
        }
        JSONObject json = new JSONObject(builder.toString());
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.getWriter().write(Parser.genJSONresp(res,System.nanoTime()-startTime));
        }
        catch (ParametersException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return;
        }
    }
}
