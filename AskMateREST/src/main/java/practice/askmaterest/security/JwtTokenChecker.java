package practice.askmaterest.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import practice.askmaterest.model.modelenum.AskRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static practice.askmaterest.security.CookieMethods.*;


public class JwtTokenChecker implements Filter {
    private final EncoderAgent encoderAgent;

    public JwtTokenChecker(EncoderAgent encoderAgent) {
        this.encoderAgent = encoderAgent;
    }

    public void doFilter(ServletRequest req, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String signature = getCookieValue(request,signatureCookie64);
        String headerPayload = getCookieValue(request,headerPayloadCookie64);

        if(Strings.isBlank(signature) || Strings.isBlank(headerPayload) ) {
            setAuthority(AskRole.UNIDENTIFIED.name(),AskRole.UNIDENTIFIED);
            filterChain.doFilter(request, response);
            return;
        }


        boolean isInvalidToken = false;
        AskRole role = AskRole.UNIDENTIFIED;
        String nameInToken = null;
        try {
            var jwt = encoderAgent.tryValidateToken(headerPayload+"."+signature);
            String requestRole = jwt.getClaim("role").toString();
            String cleanedRole = requestRole.substring(1, requestRole.length() - 1);
            nameInToken = jwt.getSubject();
            role = AskRole.valueOf(cleanedRole);
        }catch (JWTVerificationException ignore) {
            isInvalidToken = true;
        }

        if (isInvalidToken) {
            setAuthority(AskRole.UNIDENTIFIED.name(),AskRole.UNIDENTIFIED);
            filterChain.doFilter(request,response);
            return;
        }

        setAuthority(nameInToken,role);
        filterChain.doFilter(request, response);
    }
    private void setAuthority(String subject, AskRole role) {
        Authentication authentication = new PreAuthenticatedAuthenticationToken(subject,null,role.getGrantedAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
