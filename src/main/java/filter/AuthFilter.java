package filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    final static Logger logger = LogManager.getLogger(AuthFilter.class);

    private static final String[] securedPaths = new String[]{"/advertisements/create","/messenger"};

    private UserService userService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        boolean prot = false;
        for (String path : securedPaths){
            if(path.equals(req.getRequestURI().substring(req.getContextPath().length()))){
                prot = true;
                break;
            }
        }
        if (prot && !userService.isAuth(req)){
            res.sendRedirect(req.getContextPath() + "/profile");
        } else {
            if(userService.isAuth(req)){
                req.setAttribute("user", userService.getUser(req));
            }
            chain.doFilter(req, res);
        }
    }
}
