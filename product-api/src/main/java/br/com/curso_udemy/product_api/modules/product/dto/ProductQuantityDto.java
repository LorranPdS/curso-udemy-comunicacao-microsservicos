package br.com.curso_udemy.product_api.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDto {

    private Integer productId;
    private Integer quantity;
}
