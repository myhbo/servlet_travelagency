package controller.command.user;

import controller.command.Command;
import controller.dto.UserDTO;
import exception.DaoException;
import model.entity.User;
import model.entity.enums.Roles;
import model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class UserUpdate implements Command {
    private static final Logger log = LogManager.getLogger();

    private final UserService userService;
    private ResourceBundle resourceBundle;

    public UserUpdate(UserService userService) {
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
        resourceBundle = ResourceBundle.getBundle("messages", ourLocale);

        long id = 0;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {

        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String[] roles = request.getParameterValues("roles");

        if (email == null && password == null && fullName == null && roles == null) {
            User user = userService.findUserById(id);
            request.setAttribute("user", user);
            request.setAttribute("roles", Roles.values());
            return "/user-update.jsp";
        }

        UserDTO userDTO = UserDTO.builder()
                .id(id)
                .email(email)
                .password(password)
                .fullName(fullName)
                .roles(getRoles(roles))
                .build();


        try {
            userService.updateUser(userDTO);
            log.info("updated userupdate");
        } catch (DaoException e) {
            request.setAttribute("user", userDTO);
            request.setAttribute("roles", Roles.values());
            return "/user-update.jsp";
        }
        return "redirect:/users";
    }
    private Set<Roles> getRoles(String[] roles) {
        if (roles != null) {
            return Arrays.stream(roles)
                    .map(Roles::valueOf)
                    .collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }
}
