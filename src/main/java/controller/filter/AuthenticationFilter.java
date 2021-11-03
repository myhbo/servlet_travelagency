package controller.filter;

import model.entity.User;
import model.entity.enums.Roles;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class AuthenticationFilter implements Filter {

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
            "/user-cabinet"
    );
    private final List<String> managerList = Arrays.asList(
            "/",
            "/index",
            "/logout",
            "/tours",
            "/user-cabinet"
    );
    private final List<String> userList = Arrays.asList(
            "/",
            "/index",
            "/logout",
            "/tours",
            "/user-cabinet"
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
        List<String> requestedLink = user.getRole()
                .stream()
                .flatMap(role -> rolesMap.get(role).stream())
                .distinct()
                .collect(Collectors.toList());
        if (requestedLink.contains(requestURI)) {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
