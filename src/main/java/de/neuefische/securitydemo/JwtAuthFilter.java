package de.neuefische.securitydemo;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getAuthToken(request);

        if (token != null && !token.isBlank()){
            try {
                Claims claims = jwtService.parseClaims(token);

            } catch (Exception e){

            }
        }
    }

    private String getAuthToken(HttpServletRequest request){
        String authorisation = request.getHeader("Authorisation");
        return authorisation != null ? authorisation.replace("Bearer", "").trim() : null;
    }

    private void setSecurityContext(Claims claims){
        List<SimpleGrantedAuthority> grantedAuthorities = ( (List<String>)claims.get("roles")).stream()
                .map(au -> new SimpleGrantedAuthority(au)).toList();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
