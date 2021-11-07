package controller.command;

import controller.command.order.*;
import controller.command.tour.*;
import controller.command.user.*;
import model.service.OrderService;
import model.service.TourService;
import model.service.UserService;

import java.util.HashMap;
import java.util.Map;


public class CommandMapper {

    private static CommandMapper commandMapper;

    public static  CommandMapper getInstance() {
        if (commandMapper == null) {
            synchronized (CommandMapper.class) {
                if (commandMapper == null) {
                    commandMapper = new CommandMapper();
                }
            }
        }
        return commandMapper;
    }

    private final Map<String, Command> commandMap = new HashMap<>();

    private CommandMapper() {
        final UserService userService = new UserService();
        final TourService tourService = new TourService();
        final OrderService orderService = new OrderService();

        commandMap.put("/index", new Home());
        commandMap.put("/login", new Login(userService));
        commandMap.put("/logout", new Logout());
        commandMap.put("/registration", new Registration(userService));

        commandMap.put("/users", new Users(userService));
        commandMap.put("/users/ban", new UserBan(userService));
        commandMap.put("/users/unban", new UserUnban(userService));
        commandMap.put("/user-cabinet", new UserCabinet(userService));
        commandMap.put("/users/update", new UserUpdate(userService));

        commandMap.put("/tours", new Tours(tourService));
        commandMap.put("/tours/add", new TourAdd(tourService));
        commandMap.put("/tours/update", new TourUpdate(tourService));
        commandMap.put("/tours/delete", new TourDelete(tourService));
        commandMap.put("/tours/toggle-hot", new TourToggleHot(tourService));

        commandMap.put("/orders", new Orders(orderService));
        commandMap.put("/orders/add", new OrderAdd(orderService));
        commandMap.put("/orders/mark-confirmed", new OrderMarkConfirmed(orderService));
        commandMap.put("/orders/mark-rejected", new OrderMarkRejected(orderService));
        commandMap.put("/orders/set-discount", new OrderSetDiscount(orderService));
    }
    public Command getCommand(String command) {
        return commandMap.getOrDefault(command, defaultValue -> "/index.jsp");
    }
}
