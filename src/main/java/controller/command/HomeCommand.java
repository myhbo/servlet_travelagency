package controller.command;

import javax.servlet.http.HttpServletRequest;

public class HomeCommand implements Command{
    public HomeCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
