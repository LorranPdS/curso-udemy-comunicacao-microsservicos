package br.com.curso_udemy.product_api.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockDto {

    private String salesId;
    private List<ProductQuantityDto> products;
}
