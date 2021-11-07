package controller.command.user;

import controller.command.Command;
import model.service.UserService;

import javax.servlet.http.HttpServletRequest;


public class Users implements Command {
    private final UserService userService;

    public Users(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = 0;
        int size = 5;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {

            }
        }
        if (request.getParameter("size") != null) {
            try {
                size = Integer.parseInt(request.getParameter("size"));
            } catch (NumberFormatException e) {

            }

        }
        long userRecords = userService.getUsersRecords();
        long userPages = (long) Math.ceil((double) userRecords / size);
        request.setAttribute("users", userService.findAllUsersPageable(page, size));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("totalPages", userPages);
        request.setAttribute("pagerSizes", new int[] {5, 10, 50, 100});
        return "/users.jsp";
    }
}
