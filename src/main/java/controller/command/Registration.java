package controller.command;

import controller.dto.NewUserDTO;
import exception.DaoException;
import model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;


public class Registration implements Command{
    private static final Logger log = LogManager.getLogger();

    private final UserService userService;
    private ResourceBundle resourceBundle;

    public Registration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String lang = (String) request.getSession().getAttribute("lang");
        Locale ourLocale;
        if (lang == null) {
            ourLocale = Locale.getDefault();
        } else {
            ourLocale = Locale.forLanguageTag(lang);
        }

        resourceBundle = ResourceBundle.getBundle("messages",
                ourLocale);

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");

        if (email == null || password == null || fullName ==null) {
            return "/registration.jsp";
        }

        NewUserDTO newUserDTO = NewUserDTO.builder()
                .email(email)
                .password(password)
                .fullName(fullName)
                .build();

        try {
            userService.makeNewUser(newUserDTO);
            log.info("Success registration");
        } catch (DaoException e) {
            log.info("Cant register");
            return "/registration.jsp";
        }
        return "redirect:/login";
    }
}
