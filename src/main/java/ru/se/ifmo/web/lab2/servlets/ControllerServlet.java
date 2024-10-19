package ru.se.ifmo.web.lab2.servlets;

import org.json.JSONObject;
import ru.se.ifmo.web.lab2.cores.Parser;
import ru.se.ifmo.web.lab2.exceptions.ParametersException;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.util.logging.*;

public class ControllerServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/areacheck").forward(req, resp);
    }
}
