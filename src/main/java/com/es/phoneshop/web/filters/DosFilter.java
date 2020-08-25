package com.es.phoneshop.web.filters;

import com.es.phoneshop.services.DosProtectionService;
import com.es.phoneshop.services.impl.DefaultDosProtectionService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {

    private DosProtectionService dosProtectionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dosProtectionService = DefaultDosProtectionService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String userIp = servletRequest.getRemoteAddr();
        if (dosProtectionService.isAllow(userIp)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).setStatus(429); // 429 - too many requests
        }
    }

    @Override
    public void destroy() {

    }
}
