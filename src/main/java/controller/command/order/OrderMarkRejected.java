package controller.command.order;

import controller.command.Command;
import model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class OrderMarkRejected implements Command {
    private static final Logger log = LogManager.getLogger();

    private final OrderService orderService;

    public OrderMarkRejected(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long orderId;
        int page = 0;
        int size = 5;
        String columnToSort = "orders.id";
        String directionToSort = "DESC";
        try {
            orderId = Long.parseLong(request.getParameter("id"));
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
        orderService.markOrderRejected(orderId);

        return "redirect:/orders" +
                "?page=" + page +
                "&size=" + size +
                "&sortCol=" + columnToSort +
                "&sortDir=" + directionToSort;
    }
}
