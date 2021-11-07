package controller.command.order;

import controller.command.Command;
import model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class Orders implements Command {
    private static final Logger log = LogManager.getLogger();
    private final OrderService orderService;

    public Orders(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = 0;
        int size = 5;
        String columnToSort = "orders.id";
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
            return "/404.jsp";
        }
        long orderRecords = orderService.orderRecords();
        long orderPages = (long) Math.ceil((double) orderRecords / size);
        request.setAttribute("orders", orderService.findAllOrdersPageable(page,
                size,
                columnToSort,
                directionToSort));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("sortCol", columnToSort);
        request.setAttribute("sortDir", directionToSort);
        request.setAttribute("totalPages", orderPages);
        request.setAttribute("pagerSizes", new int[] {5, 10, 50, 100});
        return "/orders.jsp";
    }
}
