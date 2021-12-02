package controller.command.tour;

import controller.command.Command;
import model.service.TourService;

import javax.servlet.http.HttpServletRequest;


public class Tours implements Command {
    private final TourService tourService;

    public Tours(TourService tourService) {
        this.tourService = tourService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        int page = 0;
        int size = 5;
        String columnToSort = "is_hot";
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
        long tourRecords = tourService.tourRecords();
        long tourPages = (long) Math.ceil((double) tourRecords / size);
        request.setAttribute("tours", tourService.findAllToursPageable(page,
                size,
                columnToSort,
                directionToSort));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("sortCol", columnToSort);
        request.setAttribute("sortDir", directionToSort);
        request.setAttribute("totalPages", tourPages);
        request.setAttribute("pagerSizes", new int[] {5, 10, 50, 100});
        return "/tours.jsp";
    }
}
