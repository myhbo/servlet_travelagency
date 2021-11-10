package controller.command.order;

import controller.command.Command;
import model.entity.User;
import model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class OrderAdd implements Command {
    private static final Logger log = LogManager.getLogger();
    private final OrderService orderService;

    public OrderAdd(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        long tourId;
        try {
            tourId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            return "/404.jsp";
        }

        orderService.createOrder(user.getId(), tourId);
        log.info("created order from user " + user.getEmail() + " with tour ID" + tourId);
        return "redirect:/user-cabinet";
    }
}
