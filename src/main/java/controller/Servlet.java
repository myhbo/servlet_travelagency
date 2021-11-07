package controller;

import controller.command.Command;
import controller.command.CommandMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;


public class Servlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private final CommandMapper commandMapper = CommandMapper.getInstance();

    public void  doPost(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().replaceFirst(
                request.getContextPath() + "/app", "");
        Command command = commandMapper.getCommand(path);
        String page = command.execute(request);
        if (page.contains("redirect")) {
            response.sendRedirect(request.getContextPath() +
                    request.getServletPath() +
                    page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
