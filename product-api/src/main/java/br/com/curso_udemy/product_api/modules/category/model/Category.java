package br.com.curso_udemy.product_api.modules.category.model;

import br.com.curso_udemy.product_api.modules.category.dto.CategoryRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    /*
    Será um método usado para transformar o que vem da requisição em
    uma classe model e assim conseguir interagir com a entidade de banco de dados

    Interessante perceber que não terá id nesse momento no request. Isso porque o id será gerado
    pela nossa sequence que está lá no banco de dados
     */
    public static Category of(CategoryRequest request){
        var category = new Category();
        BeanUtils.copyProperties(request, category);
        return category;
    }
}
