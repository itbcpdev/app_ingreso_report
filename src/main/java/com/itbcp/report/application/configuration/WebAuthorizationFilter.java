package com.itbcp.report.application.configuration;

import com.itbcp.report.shared.WebExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        /*try {
            if (checkJWTToken(request, response)) {
                Claims claims = Jwts
                        .parserBuilder()
                        .setSigningKey(getKey().getBytes())
                        .build()
                        .parseClaimsJws(request.getHeader(HEADER).replace(PREFIX, ""))
                        .getBody();

                String tokenType = claims.get("token_type", String.class);

                if (tokenType.equals("0")) {
                    chain.doFilter(request, response);
                    return;
                }
            }

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {

        }

        WebExceptionResponse webExceptionResponse = new WebExceptionResponse();
        webExceptionResponse.setTitle("Aviso");
        webExceptionResponse.setMessage("Estimado usuario, debe iniciar sesión antes de acceder a esta funcionalidad");
        webExceptionResponse.setStatus(401);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(webExceptionResponse));
        response.flushBuffer();*/

        chain.doFilter(request, response);
        return;
    }
/*
    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
            return false;
        return true;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
 */
}