package ru.se.ifmo.web.lab3.filters;

import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentFilter implements Filter {
    private FilterConfig config = null;
    private boolean active = false;
    @Override
    public void init (FilterConfig config) throws ServletException
    {
        this.config = config;
        String act = config.getInitParameter("active");
        if (act != null)
            active = (act.toUpperCase().equals("TRUE"));
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = httpReq.getReader();
        reader.mark(1024);
        String newline = "";
        while ((newline = reader.readLine()) != null) {
            builder.append(newline);
        }
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.log(Level.INFO,"POST " + builder.toString() + " with size " + builder.toString().length());
        if (builder.toString().length() == 0) {
            logger.log(Level.INFO, "MOVING TO NEXT FILTER");
            chain.doFilter(req, resp);
            return;
        }
        logger.log(Level.INFO, "YET IM A FUCKING RETARD");
        JSONObject json = new JSONObject(builder.toString());
        String agent = json.getString("agent");
        ServletContext context = req.getServletContext();
        if (agent != null) {
            HashMap<String, Long> requests = (HashMap<String, Long>)context.getAttribute("requests");
            if (requests == null) {
                requests = new HashMap<String, Long>();
            }
            requests.put(agent, (requests.containsKey(agent) ? requests.get(agent) + 1 : 1));
            context.setAttribute("requests", requests);
        }
        logger.log(Level.INFO, "Resetting");
        try {
            reader.reset();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        logger.log(Level.INFO, "Moving");
        chain.doFilter(req, resp);
    }
    @Override
    public void destroy()
    {
        config = null;
    }
}
