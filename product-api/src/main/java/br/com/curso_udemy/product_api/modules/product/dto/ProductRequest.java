package br.com.curso_udemy.product_api.modules.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private Integer supplierId; // não preciso do objeto Supplier inteiro, apenas seu id
    private Integer categoryId; // não preciso do objeto Category inteiro, apenas seu id
    /*
        Observações importantes:
        1. como eu coloquei esse @JsonProperty, então a maneira como está dentro dos parênteses do
            @JsonProperty é que eu irei colocar no JSON lá do meu Postman
        2. eu defini um @JsonProperty para o atributo "quantityAvailable" porque não é uma prática
            interessante que o JSON tenha esse camelCase "quantityAvailable" vindo no payload, mas
            sim um "quantity_available", ou seja, com underscore
     */
    @JsonProperty("quantity_available")
    private Integer quantityAvailable;

}
