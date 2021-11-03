package controller.command.user;

import controller.command.Command;
import model.entity.User;
import model.service.UserService;

import javax.servlet.http.HttpServletRequest;


public class UserCabinet implements Command {
    private final UserService userService;

    public UserCabinet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", userService.findUserById(user.getId()));
        return "/user-cabinet.jsp";
    }
}
