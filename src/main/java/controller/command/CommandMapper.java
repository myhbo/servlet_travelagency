package controller.command;

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

        commandMap.put("/index", new HomeCommand());
        commandMap.put("/login", new LoginCommand(userService));
        commandMap.put("/logout", new LogoutCommand());
        commandMap.put("/registration", new RegistrationCommand(userService));
    }
    public Command getCommand(String command) {
        return commandMap.getOrDefault(command, defaultValue -> "/index.jsp");
    }
}
