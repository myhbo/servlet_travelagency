package controller.command;

import model.entity.User;
import model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;


public class LoginCommand implements Command {
    private final UserService userService;
    private static final Logger log = LogManager.getLogger();

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null && password == null) {
            return "/login.jsp";
        }

        log.info("Log in " + email);

        Optional<User> userOptional = userService.findUserByEmail(email);

        if (!userOptional.isPresent()) {

            log.info("No user with email " + email);

            return "/login.jsp";
        }

        User user = userOptional.get();

        if (!user.isEnabled()) {
            return "/login.jsp";
        }

        if (user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("User", user);
            return "redirect:/index";

        } else {
            return "/login.jsp";
        }
    }
}
