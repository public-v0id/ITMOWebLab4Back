package ru.se.ifmo.web.lab2.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.Instant;
import java.util.logging.*;

public class ControllerServlet extends HttpServlet {
    private static final String RESULT_JSON = """
		{
			"res": %b,
			"exTime": %s,
			"servTime": %s
		}
		""";
    public String genJSONresp(boolean res, double applTime) {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.log(Level.INFO, "Started generating");
        String json = String.format(RESULT_JSON, res, "\"" + Double.toString(applTime) + "\"", "\"" + Instant.now().toString() + "\"");
        logger.log(Level.INFO, json);
        json = json.trim();
        return json;
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //req.getRequestDispatcher("ru.se.ifmo.web.lab2.servlets.AreaCheckServlet").forward(req, resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(genJSONresp(true, 1));
    }
}
