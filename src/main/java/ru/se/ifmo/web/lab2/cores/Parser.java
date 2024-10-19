package ru.se.ifmo.web.lab2.cores;

import java.time.Instant;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.*;
import ru.se.ifmo.web.lab2.classes.*;
import java.util.logging.*;
import java.util.*;
import ru.se.ifmo.web.lab2.exceptions.*;

public class Parser {

    private static final String HTTP_SUCCESS = """
		Content-Type: application/json
		Content-Length: %d
		
		%s

		""";
    private static final String HTTP_ERROR = """
		HTTP/1.1 400 Bad Request
		Content-Type: application/json
		Content-Length: %d

		%s
		""";
    private static final String RESULT_JSON = """
		{
			"res": %b,
			"exTime": %s,
			"servTime": %s
		}
		""";
    private static final String JSON_ERROR = """
		{
			"reason": "%s"
		}
		""";

    public static Parameters JSONtoParams(String query) throws JSONConvException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        String s = query.replace("{", "").replace("}", "");
        if (s == null) {
            throw new JSONConvException("Empty query! Couldn't parse to parameters");
        }
        String[] pair = s.split(",");
        double x = 0;
        double y = 0;
        double r = 0;
        boolean xSet = false;
        boolean ySet = false;
        boolean rSet = false;
        for (String str: pair) {
            String[] cur = str.split(":");
            if (cur.length >= 2) {
                cur[0] = cur[0].replaceAll("\"", "");
                switch(cur[0]) {
                    case "x":
                    {
                        String val = cur[1].replaceAll("\"", "");
                        if (val.length() > 10) {
                            throw new JSONConvException("error! too long value!");
                        }
                        try {
                            x = Double.parseDouble(val);
                        }
                        catch (Exception e) {
                            throw new JSONConvException("error! too long value!");
                        }
                        xSet = true;
                    }
                    break;
                    case "y":
                    {
                        String val = cur[1].replaceAll("\"", "");
                        if (val.length() > 10) {
                            throw new JSONConvException("error! too long value!");
                        }
                        try {
                            y = Double.parseDouble(val);
                        }
                        catch (Exception e) {
                            throw new JSONConvException("error! too long value!");
                        }
                        ySet = true;
                    }
                    break;
                    case "r":
                    {
                        String val = cur[1].replaceAll("\"", "");
                        if (val.length() > 10) {
                            throw new JSONConvException("error! too long value!");
                        }
                        try {
                            r = Double.parseDouble(val);
                        }
                        catch (Exception e) {
                            throw new JSONConvException("error! too long value!");
                        }
                        rSet = true;
                    }
                    break;
                }
            }
        }
        if (!xSet || !ySet || !rSet) {
            throw new JSONConvException("Not all parameters are met in JSON! Couldn't create object!");
        }
        logger.log(Level.INFO, Double.toString(x) + " " + Double.toString(y) + " " + Double.toString(r));
        try {
            return new Parameters(x, y, r);
        }
        catch (ParametersException e) {
            throw new JSONConvException("Unable to create Parameters object!");
        }
    }

    public static String genJSONresp(boolean res, double applTime) {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.log(Level.INFO, "Started generating");
        String json = String.format(RESULT_JSON, res, "\"" + Double.toString(applTime) + "\"", "\"" + Instant.now().toString() + "\"");
        logger.log(Level.INFO, json);
        json = json.trim();
        return json;
    }
}