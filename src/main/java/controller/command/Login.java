package controller.command;

import configuration.BCryptConfig;
import model.entity.User;
import model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.function.Function;


public class Login implements Command {
    private static final Logger log = LogManager.getLogger();

    private static final BCryptConfig bcrypt = new BCryptConfig(10);
    private String[] mutableHash = new String[1];
    Function<String, Boolean> update = hash -> { mutableHash[0] = hash; return true; };

    public static boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
        return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

    private final UserService userService;

    public Login(UserService userService) {
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
            request.setAttribute("error", true);
            log.info("No user with email " + email);
            return "/login.jsp";
        }

        User user = userOptional.get();

        if (!user.isEnabled()) {
            request.setAttribute("error", true);
            return "/login.jsp";
        }

        if (bcrypt.verifyAndUpdateHash(password, user.getPassword(), update)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/index";

        } else {
            request.setAttribute("error", true);
            return "/login.jsp";
        }
    }
}
