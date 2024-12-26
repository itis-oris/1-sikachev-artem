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
import service.AdminService;
import service.UserService;

import java.io.IOException;

@WebFilter("/*")
public class AdminFilter extends HttpFilter {

    final static Logger logger = LogManager.getLogger(AdminFilter.class);

    private static final String[] securedPaths = new String[]{"/advertisements/check", "/advertisements/check/detail"};

    private AdminService adminService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        adminService = (AdminService) getServletContext().getAttribute("adminService");
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
        if (prot && !adminService.isRole(req)){
            res.sendRedirect(req.getContextPath() + "/");
        } else {
            chain.doFilter(req, res);
        }
    }
}
