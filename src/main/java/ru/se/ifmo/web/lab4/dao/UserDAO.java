package ru.se.ifmo.web.lab4.dao;

import jakarta.persistence.*;
import ru.se.ifmo.web.lab4.beans.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private EntityManagerFactory entityManagerFactory;
    private Logger logger;
    public UserDAO() {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        entityManagerFactory = Persistence.createEntityManagerFactory("user");
    }

    public List<User> findUsersByLogin(String login) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM user u WHERE u.login = :login", User.class);
            query.setParameter("login", login);
            return query.getResultList();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "findUsersByLogin " + e.getMessage());
        }
        finally {
            entityManager.close();
        }
        return null;
    }

    public boolean signIn(User user, User goal) {
        try {
            String encodedPassword = new String(MessageDigest.getInstance("MD5").digest((goal.getSalt()+user.getPassword()+"GRAFF").getBytes(StandardCharsets.UTF_8)));
            if (goal.getEncodedPassword().equals(encodedPassword)) {
                return true;
            }
            return false;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    public void createUser(User user) {
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < random.nextInt(5)+5; ++i) {
            randomString.append((char)random.nextInt(255));
        }
        user.setSalt(randomString.toString());
        user.setEncodedPassword(user.getPassword());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
