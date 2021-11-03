package controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class Logout implements Command{
    public Logout() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("success", true);
        return "login.jsp";
    }
}
