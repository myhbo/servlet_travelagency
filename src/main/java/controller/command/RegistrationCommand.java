package controller.command;

import controller.dto.NewUserDTO;
import exception.DaoException;
import model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;


public class RegistrationCommand implements Command{
    private final UserService userService;
    private ResourceBundle resourceBundle;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String lang = (String) request.getSession().getAttribute("lang");
        resourceBundle = ResourceBundle.getBundle("messages",
                Locale.forLanguageTag(lang));

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
        } catch (DaoException e) {
            return "/registration.jsp";
        }
        return "redirect:/login";
    }
}
