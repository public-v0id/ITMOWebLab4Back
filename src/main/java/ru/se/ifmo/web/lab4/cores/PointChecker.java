package ru.se.ifmo.web.lab4.cores;

import ru.se.ifmo.web.lab4.beans.Point;
import ru.se.ifmo.web.lab4.beans.Results;
import ru.se.ifmo.web.lab4.beans.User;
import ru.se.ifmo.web.lab4.dao.ResultsDAO;
import ru.se.ifmo.web.lab4.dao.UserDAO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PointChecker {
    static Logger logger;
    static ResultsDAO resultsDAO;
    static {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
    public static List<Results> getResults(String login){
        logger.log(Level.INFO, "Got user with login " + login);
        try {List<Results> res = resultsDAO.getAllResults(login);
            resultsDAO = new ResultsDAO();
            return res;
        }
        catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }
    public static Results addResult(Point point) {
        try {
            resultsDAO = new ResultsDAO();
            return resultsDAO.addResult(point);
        }
        catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }
}
