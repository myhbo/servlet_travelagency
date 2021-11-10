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
        String columnToSort = "id";
        String directionToSort = "DESC";
        try {
            if (request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
            if (request.getParameter("size") != null)
                size = Integer.parseInt(request.getParameter("size"));
            if (request.getParameter("sortCol") != null)
                columnToSort = request.getParameter("sortCol");
            if (request.getParameter("sortDir") != null)
                directionToSort = request.getParameter("sortDir");
        } catch (NumberFormatException e) {
            return "404.jsp";
        }

        long userRecords = userService.getUsersRecords();
        long userPages = (long) Math.ceil((double) userRecords / size);
        request.setAttribute("users", userService.findAllUsersPageable(page,
                size,
                columnToSort,
                directionToSort));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("totalPages", userPages);
        request.setAttribute("sortCol", columnToSort);
        request.setAttribute("sortDir", directionToSort);
        request.setAttribute("pagerSizes", new int[] {5, 10, 50, 100});
        return "/users.jsp";
    }
}
