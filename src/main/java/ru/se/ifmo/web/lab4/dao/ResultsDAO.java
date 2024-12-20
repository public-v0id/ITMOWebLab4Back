package ru.se.ifmo.web.lab4.dao;

import jakarta.persistence.*;
import ru.se.ifmo.web.lab4.beans.Point;
import ru.se.ifmo.web.lab4.beans.Results;
import ru.se.ifmo.web.lab4.beans.User;
import ru.se.ifmo.web.lab4.cores.Validator;
import ru.se.ifmo.web.lab4.exceptions.WrongCoordinatesException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultsDAO {
    private EntityManagerFactory entityManagerFactory;
    private Logger logger;
    public ResultsDAO() {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        entityManagerFactory = Persistence.createEntityManagerFactory("results");
    }

    public List<Results> getAllResults(String login) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Results> query = entityManager.createQuery("SELECT r FROM results r WHERE r.login = :login", Results.class);
            query.setParameter("login", login);
            return query.getResultList();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "getAllResults " + e.getMessage());
        }
        finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return null;
    }

    public Results addResult(Point point) throws WrongCoordinatesException {
        long stTime = System.currentTimeMillis();
        Results res = new Results(point.getX(), point.getY(), point.getR(), Validator.check(point.getX(), point.getY(), point.getR()), 0, Calendar.getInstance().getTime().toString(), point.getLogin());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        res.setExTime((System.currentTimeMillis()-stTime)/1000.0);
        entityManager.persist(res);
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
        return res;
    }
}
