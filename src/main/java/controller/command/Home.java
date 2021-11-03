package controller.command;

import javax.servlet.http.HttpServletRequest;

public class Home implements Command{
    public Home() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
