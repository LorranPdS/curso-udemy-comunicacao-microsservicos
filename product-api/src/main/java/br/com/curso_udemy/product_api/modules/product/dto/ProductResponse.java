package br.com.curso_udemy.product_api.modules.product.dto;

import br.com.curso_udemy.product_api.modules.category.dto.CategoryResponse;
import br.com.curso_udemy.product_api.modules.product.model.Product;
import br.com.curso_udemy.product_api.modules.supplier.dto.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder // o @Builder nos exige que tenhamos um construtor, por isso coloquei abaixo
@AllArgsConstructor
public class ProductResponse {

    private Integer id;
    private String name;
    private SupplierResponse supplier; // coloquei 'supplier' para que venha assim no JSON
    private CategoryResponse category; // a mesma coisa fiz com o 'category'
    private Integer quantityAvailable;

    /*
        1. colocamos @JsonProperty para que ele venha no nosso Json como "created_at"
        em vez de aparecer como "createdAt".
        2. já o @JsonFormat nós estamos definindo como virá o formato dessa data para nós
     */
    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    public static ProductResponse of(Product product){
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .supplier(SupplierResponse.of(product.getSupplier()))
                .category(CategoryResponse.of(product.getCategory()))
                .quantityAvailable(product.getQuantityAvailable())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
