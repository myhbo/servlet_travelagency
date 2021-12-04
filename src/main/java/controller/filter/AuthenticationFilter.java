package controller.filter;

import model.entity.User;
import model.entity.enums.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class AuthenticationFilter implements Filter {
    private static final Logger log = LogManager.getLogger();

    private final List<String> anonymousList = Arrays.asList(
            "/",
            "/index",
            "/login",
            "/registration",
            "/tours"
    );
    private final List<String> adminList = Arrays.asList(
            "/",
            "/index",
            "/logout",
            "/tours",
            "/users",
            "/users/update",
            "/users/ban",
            "/users/unban",
            "/user-cabinet",
            "/tours/add",
            "/tours/update",
            "/tours/delete",
            "/tours/toggle-hot",
            "/orders",
            "/orders/add",
            "/orders/mark-confirmed",
            "/orders/mark-rejected",
            "/orders/set-discount"
    );
    private final List<String> managerList = Arrays.asList(
            "/",
            "/index",
            "/logout",
            "/tours",
            "/user-cabinet",
            "/tours/toggle-hot",
            "/orders",
            "/orders/add",
            "/orders/mark-confirmed",
            "/orders/mark-rejected",
            "/orders/set-discount"
    );
    private final List<String> userList = Arrays.asList(
            "/",
            "/index",
            "/logout",
            "/tours",
            "/user-cabinet",
            "/orders/add"
    );

    private final Map<Roles, List<String>> rolesMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        rolesMap.put(Roles.ADMIN, adminList);
        rolesMap.put(Roles.MANAGER, managerList);
        rolesMap.put(Roles.USER, userList);

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String requestURI = request.getRequestURI().replaceFirst(
                request.getContextPath() + "/app", "");

        User user = (User) session.getAttribute("user");
        if (user == null) {
            if (anonymousList.contains(requestURI)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() +
                        request.getServletPath() + "/login");
            }
            return;
        }
        Roles userRole = user.getRole();
        List<String> requestedLink = new ArrayList<>(rolesMap.get(userRole));

        if (requestedLink.contains(requestURI)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(403);
            request.getRequestDispatcher("/403.jsp").forward(request, response);
            log.info(user.getEmail() + "try to access forbidden page");
        }
    }
}
