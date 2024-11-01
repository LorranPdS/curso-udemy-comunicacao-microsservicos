package br.com.curso_udemy.product_api.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** CONFIGURANDO O AuthInterceptor
 *
 * Para configurarmos a classe AuthInterceptor, precisaremos criar um Bean do Spring
 * para indicar para ele que nós precisaremos ter esse interceptor dentro da nossa
 * aplicação. Essa nossa configuração é nessa classe 'InterceptorConfig' que estamos
 * criando aqui.
 *
 * Para começar, iremos implementar uma interface que temos no Spring chamada 'WebMvcConfigurer'
 * que irá tratar de configurar uns Controllers do nosso Spring (WebMVC).
 *
 * Essa nossa classe terá uma anotação @Configuration porque essa classe será uma classe de
 * configuração. Quando o Spring for subir, ele já vai identificar que é uma classe de
 * configuração, logo ele irá incluir isso nas configurações dos beans do Spring.
 *
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /** CRIAÇÃO DO BEAN
     *
     * Então nesse método criamos um Bean para incluir nas configurações do Spring
     * e colocar o nosso interceptor que criamos
     *
     */
    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }


    /**
     * Vamos também implementar esse método 'addInterceptors', sendo ele um dos métodos
     * que estão na interface 'WebMvcConfigurer'. Vamos pegar esse bean que fizemos acima
     * (no caso o método que tem a anotação @Bean da nossa classe, o método 'authInterceptor()')
     * e então iremos tratar esse 'authInterceptor()' para adicionar ele com o novo interceptor
     * da aplicação, e dessa forma estaremos dizendo ao framework que queremos usar isso como
     * um interceptador de requisições.
     *
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor());
    }
}
