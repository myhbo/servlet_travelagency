package controller.command.user;

import controller.command.Command;
import model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class UserUnban implements Command {
    private final UserService userService;

    public UserUnban(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id = 0;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {

        }
        userService.unbanUser(id);
        return "redirect:/users";
    }
}
