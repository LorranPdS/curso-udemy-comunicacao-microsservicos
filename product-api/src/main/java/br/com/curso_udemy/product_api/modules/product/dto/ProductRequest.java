package br.com.curso_udemy.product_api.modules.product.dto;

import br.com.curso_udemy.product_api.modules.category.model.Category;
import br.com.curso_udemy.product_api.modules.supplier.model.Supplier;
import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private Integer supplierId; // não preciso do objeto Supplier inteiro, apenas seu id
    private Integer categoryId; // não preciso do objeto Category inteiro, apenas seu id
    private Integer quantityAvailable;

}
