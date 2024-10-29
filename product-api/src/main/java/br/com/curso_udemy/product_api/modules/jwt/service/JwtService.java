package br.com.curso_udemy.product_api.modules.jwt.service;

import br.com.curso_udemy.product_api.config.exception.AuthenticationException;
import br.com.curso_udemy.product_api.modules.jwt.dto.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class JwtService {

    private static final String BEARER = "bearer ";

    // esse caminho abaixo aponta para o conteúdo do arquivo "application.yml"
    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public void validateAuthorization(String token){
        try{
            var accessToken = extractedToken(token);
            var claims = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

            var user = JwtResponse.getUser(claims);
            if(isEmpty(user) || isEmpty(user.getId())){
                throw new AuthenticationException("The user is not valid.");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            throw new AuthenticationException("Error while trying to process the Access Token.");
        }
    }

    private String extractedToken(String token){
        if(isEmpty(token)){
            throw new AuthenticationException("The access token was not informed.");
        }
        if(token.toLowerCase().contains(BEARER)){
            token = token.toLowerCase();
            token = token.replace(BEARER, Strings.EMPTY);
        }
        return token;
    }
}
