package ru.se.ifmo.web.lab3.beans;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.se.ifmo.web.lab3.cores.HibernateUtil;
import ru.se.ifmo.web.lab3.cores.Validator;
import ru.se.ifmo.web.lab3.data.Results;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "Validator")
@SessionScoped
public class ValidatorBean implements Serializable{
    private List<Results> res;
    @ManagedProperty("#{Point}")
    private PointBean pointBean;

    private String lastHit;

    public void setLastHit(String lastHit) {
        this.lastHit = lastHit;
    }

    public String getLastHit() {
        return lastHit;
    }

    public void setPointBean(PointBean pointBean) {
        this.pointBean = pointBean;
    }

    public PointBean getPointBean() {
       return pointBean;
    }

    public List<Results> getRes() {
        return res;
    }
    public void setRes(ArrayList<Results> res) {
        this.res = res;
    }
    @PostConstruct
    public void init() {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.log(Level.INFO, "Validator created");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Results> criteria = builder.createQuery(Results.class);
            criteria.from(Results.class);
            res = session.createQuery(criteria).getResultList();
            lastHit = "unknown";
        }
        catch (Exception e) {
            res = new ArrayList<Results>();
            e.printStackTrace();
        }
    }

    public void addRes() {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.log(Level.INFO, "Adding res");
        long stTime = System.currentTimeMillis();
        boolean hit = Validator.check(pointBean.getX(), pointBean.getY(), pointBean.getR());
        lastHit = hit ? "true" : "false";
        Results cur = new Results(pointBean.getX(), pointBean.getY(), pointBean.getR(), hit, (System.currentTimeMillis()-stTime), Calendar.getInstance().getTime().toString());
        logger.log(Level.INFO, "Adding to list");
        res.add(cur);
        saveRes(cur);
    }
    public String getResSize() {
        return Integer.toString(res.size());
    }
    public void saveRes(Results res) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(res);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}