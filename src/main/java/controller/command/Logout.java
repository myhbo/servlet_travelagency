package controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class Logout implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.setAttribute("success", true);
        return "redirect:/login.jsp";
    }
}
