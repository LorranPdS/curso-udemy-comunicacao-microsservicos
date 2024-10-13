package br.com.curso_udemy.product_api.modules.produto.dto;

import lombok.Data;

/*
Esse CategoryRequest será apenas uma informação de requisição que irá entrar.
IMPORTANTE: nós jamais passaremos as nossas models nas nossas entradas e saídas!
tudo que for entrada e saída, nós teremos uma informação específica para isso, mas
NUNCA mandaremos a nossa model do banco de dados direto
 */
@Data
public class CategoryRequest {

    /*
    Perceba que não temos o atributo id. Isso porque o id ainda não existe e será
    gerado pelo banco de dados, até porque esse daqui é o request
     */

    private String description;
}
