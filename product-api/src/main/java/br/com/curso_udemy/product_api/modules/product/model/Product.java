package br.com.curso_udemy.product_api.modules.product.model;

import br.com.curso_udemy.product_api.modules.category.model.Category;
import br.com.curso_udemy.product_api.modules.product.dto.ProductRequest;
import br.com.curso_udemy.product_api.modules.product.dto.ProductResponse;
import br.com.curso_udemy.product_api.modules.supplier.model.Supplier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "FK_SUPPLIER")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY")
    private Category category;

    @Column(name = "QUANTITY_AVAILABLE", nullable = false)
    private Integer quantityAvailable;

    // a propriedade 'updatable' significa que o atributo nunca será atualizado
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /*
        Com a criação do método abaixo, ela será persistida apenas uma vez, e essa
        uma vez persistida será quando criarmos um objeto no banco de dados, e assim
        não precisaremos especificar ela no método de baixo
     */
    @PrePersist
    public void prepersist(){
        createdAt = LocalDateTime.now();
    }

    public static Product of(ProductRequest request, Supplier supplier, Category category){
        return Product.builder()
                .name(request.getName())
                .supplier(supplier)
                .category(category)
                .quantityAvailable(request.getQuantityAvailable())
                .build();
    }
}
