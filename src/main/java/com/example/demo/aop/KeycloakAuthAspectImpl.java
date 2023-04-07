package com.example.demo.aop;
import com.example.demo.model.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@Aspect
@Component
public class KeycloakAuthAspectImpl implements KeycloakAuthAspect{
    @Autowired
    private final CurrentUser currentUser;
    @Autowired
    HttpServletRequest request;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Pointcut("@annotation(com.example.demo.aop.KeycloakAuth)")
    public void tokenPointcut() {
        // pointcut
    }
    @Around("tokenPointcut()")
    @Override
    public Object checkToken(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = request.getHeader("Authorization");
        if (token !=null) {
            try{
                if(token.contains("Bearer"))
                    token = token.substring(7);
                Jwt jwt = jwtDecoder.decode(token);
                Map<String, Object> map = jwt.getClaims();
                currentUser.setId(map.get("sub").toString());
                currentUser.setName(map.get("name").toString());
            }
            catch (Exception e){
                throw new AuthenticationException("error.auth.invalid_token");
            }
            return joinPoint.proceed();
        }
        throw new AuthenticationException("error.auth.no_token");
    }
}
