package controller.command.user;

import controller.command.Command;
import controller.dto.UserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import model.entity.User;
import model.entity.enums.Roles;
import model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;

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
            return "/404.jsp";
        } catch (Exception e) {
            return "/500.jsp";
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

        final Validator VALIDATOR =
                Validation.byDefaultProvider()
                        .configure()
                        .messageInterpolator(new ResourceBundleMessageInterpolator(
                                new AggregateResourceBundleLocator(Arrays.asList("messages"))))
                        .buildValidatorFactory()
                        .getValidator();

        Set<ConstraintViolation<UserDTO>> violations = VALIDATOR.validate(userDTO);

        log.info("try to set discount");

        if (violations.isEmpty()) {
            userService.updateUser(userDTO);

            return "redirect:/users";
        } else {
            request.setAttribute("user", userDTO);
            request.setAttribute("roles", Roles.values());
            request.setAttribute("errors", violations);
            return "/user-update.jsp";
        }
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
