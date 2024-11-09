package ru.se.ifmo.web.lab2.cores;

import java.time.Instant;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;

import org.json.JSONObject;
import ru.se.ifmo.web.lab2.classes.*;
import java.util.logging.*;
import java.util.*;
import ru.se.ifmo.web.lab2.exceptions.*;

import javax.servlet.http.HttpServletRequest;

public class Parser {
    private static final String RESULT_JSON = """
		{
			"res": %s,
			"exTime": %s,
			"servTime": %s
		}
		""";

    public static Parameters JSONtoParams(HttpServletRequest req) throws JSONConvException, IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();
        String newline = "";
        while ((newline = reader.readLine()) != null) {
            builder.append(newline);
        }
        JSONObject json = new JSONObject(builder.toString());
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        if (json.getString("x") == null || json.getString("y") == null || json.getString("r") == null || json.getString("x").length() > 10 || json.getString("y").length() > 10 || json.getString("r").length() > 10) {
            throw new JSONConvException("Not all parameters are present or some are too long!");
        }
        double x = 0, y = 0, r = 0;
        try {
            x = Double.parseDouble(json.getString("x"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "X is not a floating point value");
        }
        try {
            y = Double.parseDouble(json.getString("y"));
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Y is not a floating point value");
        }
        try {
            r = Double.parseDouble(json.getString("r"));
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "R is not a floating point value");
        }
        logger.log(Level.INFO, Double.toString(x) + " " + Double.toString(y) + " " + Double.toString(r));
        try {
            return new Parameters(x, y, r);
        }
        catch (WrongParametersException e) {
            throw new JSONConvException("Unable to create Parameters object!");
        }
    }

    public static String genJSONresp(DTO res) {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.log(Level.INFO, "Started generating");
        String json = String.format(RESULT_JSON, res.getRes(), "\"" +  res.getExTime() + "\"", "\"" + res.getServTime() + "\"");
        logger.log(Level.INFO, json);
        json = json.trim();
        return json;
    }

    public static DTO ParamsToDTO(Parameters params, long startTime) {
        return new DTO(doubleToString(params.getX()), doubleToString(params.getY()), doubleToString(params.getR()), Boolean.toString(Validator.check(params)), Long.toString(System.nanoTime()-startTime),Instant.now().toString());
    }

    public static String doubleToString(double x) {
        String res = Double.toString(x);
        if (res.endsWith(".0")) {
            res = res.substring(0, res.length()-2);
        }
        return res;
    }
}