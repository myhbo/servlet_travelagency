package controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class LocalisationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getParameter("lang") != null) {
            request.getSession().setAttribute("lang", request.getParameter("lang"));
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
