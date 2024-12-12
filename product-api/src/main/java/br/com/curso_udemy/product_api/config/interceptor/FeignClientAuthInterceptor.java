package br.com.curso_udemy.product_api.config.interceptor;

import br.com.curso_udemy.product_api.config.exception.ValidationException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/*
    O que irá acontecer aqui é o seguinte:
        Antes mesmo dele chamar o FeignClient, ele vai setar o nosso header
        chamado "Authorization" (esse que é uma constant) e irá pegar o nosso
        header "Authorization" que está na nossa requisição ('.getHeader(AUTHOR...)')
        que é o nosso Token de acesso e depois repassar para nossa aplicação.
        A consequência disso é que estaremos sempre autenticados quando formos chamar
        a aplicação de 'salesApi'
 */
public class FeignClientAuthInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    /*
        Vemos logo abaixo que nosso método irá lá no Header (são aqueles conteúdos
        que vemos no front ao clicar em F12 com vários preenchimentos) e dentro
        dele vamos pegar o que está no valor no "Authorization" e pegar o conteúdo
        dele, que no caso é o TOKEN.
     */
    @Override
    public void apply(RequestTemplate template) {
        var currentRequest = getCurrentRequest();
        template.
                header(AUTHORIZATION, currentRequest.getHeader(AUTHORIZATION));
    }

    private HttpServletRequest getCurrentRequest(){
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes())
                    .getRequest();
        } catch (Exception ex){
            ex.printStackTrace();
            throw new ValidationException("The current request could not be processed.");
        }
    }
}
