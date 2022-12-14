package practice.askmaterest.security;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static practice.askmaterest.security.EncoderAgent.base64Encode;

@Component
public class CookieMethods {
    public static final String signatureCookie64 = base64Encode("signatureCookie");
    public static final String headerPayloadCookie64 = base64Encode("headerPayloadCookie");


    public static String getCookieValue(HttpServletRequest req, String cookieName) throws NullPointerException{
        if(req.getCookies() == null) return null;
        return Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    public static Set<Cookie> getJWTCookies(String jwt) {
        Set<Cookie> cookies = new HashSet<>();
        var jwtSegments = jwt.split("\\.");

        Cookie signatureCookie = new Cookie(CookieMethods.signatureCookie64, jwtSegments[2]);
        Cookie payloadCookie = new Cookie(CookieMethods.headerPayloadCookie64, jwtSegments[0] + "." + jwtSegments[1]);
        signatureCookie.setPath("/");
        payloadCookie.setPath("/");
        signatureCookie.setHttpOnly(true);
        signatureCookie.setSecure(true);
        payloadCookie.setSecure(true);
        payloadCookie.setMaxAge(-1);
        signatureCookie.setMaxAge(-1);
        payloadCookie.setDomain("localhost");
        signatureCookie.setDomain("localhost");

        cookies.add(signatureCookie);
        cookies.add(payloadCookie);
        return cookies;
    }

    public static Set<Cookie> getEmptyCookies() {
        Set<Cookie> cookies = new HashSet<>();

        Cookie signatureCookie = new Cookie(CookieMethods.signatureCookie64,null);
        Cookie payloadCookie = new Cookie(CookieMethods.headerPayloadCookie64, null);
        signatureCookie.setPath("/");
        payloadCookie.setPath("/");
        signatureCookie.setHttpOnly(true);
        signatureCookie.setSecure(true);
        payloadCookie.setSecure(true);
        payloadCookie.setMaxAge(0);
        signatureCookie.setMaxAge(0);
        payloadCookie.setDomain("localhost");
        signatureCookie.setDomain("localhost");

        cookies.add(signatureCookie);
        cookies.add(payloadCookie);
        return cookies;
    }
}
