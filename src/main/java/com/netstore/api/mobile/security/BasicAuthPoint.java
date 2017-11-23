package com.netstore.api.mobile.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Master on 2017-09-19.
 */
@Component
public class BasicAuthPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        PrintWriter writer = response.getWriter();
//        writer.println();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("DeveloperStack");
        super.afterPropertiesSet();
    }
}
