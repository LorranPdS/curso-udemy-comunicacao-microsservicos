package br.com.curso_udemy.product_api.modules.sales.client;

import br.com.curso_udemy.product_api.modules.sales.dto.SalesProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/*
    1. importante informar que, ao usar a annotation abaixo, é necessário ir na classe
    em que contém o 'main' e habilitar o FeignClient com uma annotation que lá foi posta por mim

    2. vemos nos parâmetros da annotation "FeignClient" abaixo que temos uma em que descreve
    a url. Nessa parte em que está a URL, ela está indicando o caminho do sales no docker que
    está nosso arquivo "application.yml". Ainda não sei ao certo, mas talvez seja interessante
    pensar que, sempre que houver essas descrições de variáveis docker em alguma String, a
    possibilidade é que a annotation está se comunicando com a parte do nosso sistema que
    possui um entendimento de docker, que no nosso caso é o arquivo do resources chamado "application.yml"
 */
@FeignClient(
        name = "salesClient",
        contextId = "salesClient",
        url = "${app-config.services.sales}"
)
public interface SalesClient {

    @GetMapping("products/{productId}")
    Optional<SalesProductResponse> findSalesByProductId(@PathVariable Integer productId);
}
