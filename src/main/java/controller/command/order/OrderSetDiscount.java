package controller.command.order;

import controller.command.Command;
import controller.dto.OrderDTO;
import controller.dto.TourDTO;
import model.entity.Order;
import model.entity.Tour;
import model.entity.enums.HotelType;
import model.entity.enums.TourType;
import model.service.OrderService;
import model.service.TourService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class OrderSetDiscount implements Command {
    public static final Logger log = LogManager.getLogger();

    private final OrderService orderService;

    public OrderSetDiscount(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String lang = (String) request.getSession().getAttribute("lang");
        Locale ourLocale;
        if (lang == null) {
            ourLocale = Locale.getDefault();
        } else {
            ourLocale = Locale.forLanguageTag(lang);
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", ourLocale);

        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            return "/404.jsp";
        }

        String discount = request.getParameter("discount");

        if (discount == null) {
            Order order = orderService.findOrderById(id);
            request.setAttribute("order", order);
            return "/discount-update.jsp";
        }
        try {
            OrderDTO orderDTO = OrderDTO.builder()
                    .discount(Double.parseDouble(discount))
                    .build();
            log.info("try to set discount");
            orderService.setDiscount(id, orderDTO);
            log.info("discount updated");
            return "redirect:/orders";
        } catch (NumberFormatException e) {
            return "/500.jsp";
        }

    }
}
