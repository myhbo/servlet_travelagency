package controller.command;

import configuration.BCryptConfig;
import controller.dto.NewUserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;


public class Registration implements Command{
    private static final Logger log = LogManager.getLogger();
    private static final BCryptConfig bcrypt = new BCryptConfig(10);

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
                .password(bcrypt.hash(password))
                .fullName(fullName)
                .build();

        final Validator VALIDATOR =
                Validation.byDefaultProvider()
                        .configure()
                        .messageInterpolator(new ResourceBundleMessageInterpolator(
                                new AggregateResourceBundleLocator(Arrays.asList("messages"))))
                        .buildValidatorFactory()
                        .getValidator();

        Set<ConstraintViolation<NewUserDTO>> violations = VALIDATOR.validate(newUserDTO);



        if (violations.isEmpty()) {
            userService.addNewUser(newUserDTO);
            log.info("Success registration");
            return "redirect:/login";
        } else {
            request.setAttribute("user", newUserDTO);
            request.setAttribute("errors", violations);
            return "/registration.jsp";
        }
    }
}
