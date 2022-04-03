package it.eg.sloth.api.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.eg.sloth.core.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertificateException;

/**
 * Project: sloth-framework
 * Copyright (C) 2022-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Slf4j
public class JwtAuthenticationCertFilter extends BasicAuthenticationFilter {

    private PublicKey publicKey;

    public JwtAuthenticationCertFilter(AuthenticationManager authManager, String certificatePath) throws IOException, CertificateException {
        super(authManager);
        publicKey = JwtUtil.getPublicKey(certificatePath);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        Jws<Claims> jws = JwtUtil.validateToken(JwtUtil.extractJwtToken(request.getHeader(JwtUtil.TOKEN_HEADER)), publicKey);
        if (jws != null) {
            return new UsernamePasswordAuthenticationToken(jws.getBody().getSubject(), null, null);
        } else {
            return null;
        }
    }

}
