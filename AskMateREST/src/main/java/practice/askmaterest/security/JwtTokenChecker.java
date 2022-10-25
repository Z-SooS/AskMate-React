package practice.askmaterest.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import practice.askmaterest.model.modelenum.AskRole;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static practice.askmaterest.security.CookieMethods.*;


public class JwtTokenChecker extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String signature = getCookieValue(request,signatureCookie64);
        String headerPayload = getCookieValue(request,headerPayloadCookie64);
        String usernameInHeader = request.getHeader("User");

        if(Strings.isBlank(signature) || Strings.isBlank(headerPayload) || Strings.isBlank(usernameInHeader)) {
            setAuthority(AskRole.UNIDENTIFIED.name(),AskRole.UNIDENTIFIED);
            filterChain.doFilter(request, response);
            return;
        }


        boolean isInvalidToken = false;
        AskRole role = AskRole.UNIDENTIFIED;
        try {
            var jwt = EncoderAgent.tryValidateToken(usernameInHeader,headerPayload+"."+signature);
            role = AskRole.valueOf(jwt.getClaim("role").toString());
        }catch (JWTVerificationException ignore) {
            isInvalidToken = true;
        }

        if (isInvalidToken) {
            setAuthority(AskRole.UNIDENTIFIED.name(),AskRole.UNIDENTIFIED);
            filterChain.doFilter(request,response);
            return;
        }

        setAuthority(usernameInHeader,role);
        filterChain.doFilter(request, response);
    }
    private void setAuthority(String subject, AskRole role) {
        Authentication authentication = new PreAuthenticatedAuthenticationToken(subject,null,role.getGrantedAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
