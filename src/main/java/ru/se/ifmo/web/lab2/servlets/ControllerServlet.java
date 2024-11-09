package ru.se.ifmo.web.lab2.servlets;

import org.json.JSONObject;
import ru.se.ifmo.web.lab2.classes.DTO;
import ru.se.ifmo.web.lab2.cores.Parser;
import ru.se.ifmo.web.lab2.exceptions.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class ControllerServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ServletContext context = getServletContext();
        List<DTO> resList = (List<DTO>)context.getAttribute("resList");
        if (resList == null) {
            resList = new ArrayList<DTO>();
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/areacheck").forward(req, resp);
    }
}
