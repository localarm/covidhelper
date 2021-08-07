package com.pavel.covhelper.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException{
        String targetUrl = determineTargetUrl(authentication);
        if(response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
        clearAuthenticationAttributes(request);
    }

    protected String determineTargetUrl(final Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
        for (final GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_Admin")) {
                return "/units";
            }
            if (authority.getAuthority().equals("ROLE_Department_viewer")) {
                SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
                return "/department/"+securityUser.getDepartmentId();
            }
        }
        // не должно происходить с набором ролей Admin и Department_viewer
        throw new IllegalStateException();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
