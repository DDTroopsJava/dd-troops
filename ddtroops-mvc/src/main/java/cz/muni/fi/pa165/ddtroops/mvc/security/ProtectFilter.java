package cz.muni.fi.pa165.ddtroops.mvc.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pstanko.
 * @author pstanko
 */
@WebFilter(urlPatterns = {"/heroes/*", "/users/*", "/roles/*", "/troops/*"})
public class ProtectFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(ProtectFilter.class);


    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        UserDTO  user = (UserDTO) request.getSession().getAttribute("user");

        if(user == null){
            log.debug("User not authorized!");
            response.sendRedirect(request.getContextPath() + "/auth");
            return;
        }

        chain.doFilter(request, response);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
