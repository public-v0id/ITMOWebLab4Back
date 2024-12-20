package ru.se.ifmo.web.lab4.cores;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import ru.se.ifmo.web.lab4.beans.User;
import ru.se.ifmo.web.lab4.dao.UserDAO;
import ru.se.ifmo.web.lab4.exceptions.UnknownLoginException;
import ru.se.ifmo.web.lab4.exceptions.UserExistsException;
import ru.se.ifmo.web.lab4.exceptions.WrongPasswordException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.security.MessageDigest.getInstance;

public class Authentificator {
    static Logger logger;
    static {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
    public static String signIn(User user) throws UnknownLoginException, WebApplicationException, WrongPasswordException {
        logger.log(Level.INFO, "Got user with login " + user.getLogin());
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.findUsersByLogin(user.getLogin());
            if (users != null && users.size() == 1) {
                if (userDAO.signIn(user, users.get(0))) {
                    return "Signed in successfully!";
                }
                throw new WrongPasswordException("Wrong password!");
            }
            else if (users != null) {
                throw new UnknownLoginException("Unknown login");
            }
            throw new WebApplicationException("Something went wrong!", Response.Status.BAD_REQUEST);
    }
    public static String register(User user) throws WebApplicationException, UserExistsException{
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.findUsersByLogin(user.getLogin());
            if (users != null && users.size() == 0) {
                userDAO.createUser(user);
                return "User registered!";
            }
            else if (users != null) {
                throw new UserExistsException("User already exists");
            }
            throw new WebApplicationException("Something went wrong!", Response.Status.BAD_REQUEST);
    }
}
