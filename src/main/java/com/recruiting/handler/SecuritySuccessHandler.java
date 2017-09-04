package com.recruiting.handler;

import com.recruiting.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Martha on 4/5/2017.
 */
//public class SecuritySuccessHandler{
public class SecuritySuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final Logger LOG = Logger.getLogger(SecuritySuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (response.isCommitted()) {
            return;
        }
        try {
            redirectStrategy.sendRedirect(request, response, SecuritySuccessHandler.resolveTargetURL(authentication, Constants.PATH_BASE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String resolveTargetURL(Authentication authentication, String noAuthenticatedPath) {
        String targetUrl = noAuthenticatedPath;
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = grantedAuthorities.iterator();
        while (iterator.hasNext()) {
            GrantedAuthority grantedAuthority = iterator.next();
            if (grantedAuthority.getAuthority().equalsIgnoreCase(Constants.ROLE_ADMIN))
                targetUrl = Constants.PATH_BASE_ADMIN;
            else if (grantedAuthority.getAuthority().equalsIgnoreCase(Constants.ROLE_CANDIDATE))
                targetUrl = Constants.PATH_BASE_CANDIDATE;
            else if (grantedAuthority.getAuthority().equalsIgnoreCase(Constants.ROLE_COMPANY))
                targetUrl = Constants.PATH_BASE_COMPANY;
        }

        LOG.warn("User: " + authentication.getName()
                + " granted access to the protected URL: "
                + targetUrl);
        return targetUrl;
    }
}
