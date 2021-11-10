package controller.command.tour;

import controller.command.Command;
import model.service.TourService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class TourToggleHot implements Command {
    private static final Logger log = LogManager.getLogger();

    private final TourService tourService;

    public TourToggleHot(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id;
        int page = 0;
        int size = 5;
        String columnToSort = "hot";
        String directionToSort = "DESC";
        try {
            id = Long.parseLong(request.getParameter("id"));
            if (request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
            if (request.getParameter("size") != null)
                size = Integer.parseInt(request.getParameter("size"));
            if (request.getParameter("sortCol") != null)
                columnToSort = request.getParameter("sortCol");
            if (request.getParameter("sortDir") != null)
                directionToSort = request.getParameter("sortDir");
        } catch (NumberFormatException e) {
            return "/404.jsp";
        } catch (IllegalAccessError e) {
            return "/500.jsp";
        }
        tourService.toggleHot(id);
        log.info("tour with id " + id + " is now hot");
        return "redirect:/tours" +
                "?page=" + page +
                "&size=" + size +
                "&sortCol=" + columnToSort +
                "&sortDir=" + directionToSort;
    }
}
