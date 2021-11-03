package controller.command;

import controller.command.user.*;
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

        commandMap.put("/index", new Home());
        commandMap.put("/login", new Login(userService));
        commandMap.put("/logout", new Logout());
        commandMap.put("/registration", new Registration(userService));

        commandMap.put("/users", new Users(userService));
        commandMap.put("/users/ban", new UserBan(userService));
        commandMap.put("/users/unban", new UserUnban(userService));
        commandMap.put("/user-cabinet", new UserCabinet(userService));
        commandMap.put("/users/update", new UserUpdate(userService));
    }
    public Command getCommand(String command) {
        return commandMap.getOrDefault(command, defaultValue -> "/index.jsp");
    }
}
