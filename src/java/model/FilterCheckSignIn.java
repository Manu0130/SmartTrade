package model;

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

@WebFilter (urlPatterns = {"/my-account.html"})
public class FilterCheckSignIn implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServleResponse = (HttpServletResponse) response;
        
        if (httpServletRequest.getSession().getAttribute("user")!=null) {
            chain.doFilter(request, response);
        } else {
            httpServleResponse.sendRedirect("sign-in.html");
        }
        
    }

    @Override
    public void destroy() {
    }
    
}
