package controller.command.user;

import controller.command.Command;
import model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class UserBan implements Command {
    private static  final Logger log = LogManager.getLogger();

    private final UserService userService;

    public UserBan(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id = 0;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            return "/404.jsp";
        } catch (IllegalAccessError e) {
            return "/500.jsp";
        }
        userService.banUser(id);
        log.info("user banned");
        return "redirect:/users";
    }
}
