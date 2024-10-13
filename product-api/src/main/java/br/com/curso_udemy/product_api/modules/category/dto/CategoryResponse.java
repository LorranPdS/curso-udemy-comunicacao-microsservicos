package br.com.curso_udemy.product_api.modules.category.dto;

import br.com.curso_udemy.product_api.modules.category.model.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoryResponse {

    // No response, ele já vai devolver o id dessa categoria
    private Integer id;
    private String description;

    /*
    Esse será um método que usaremos para converter o model "Category" (do banco de dados)
    para o "CategoryResponse".

    Como todos os campos serão exatamente os mesmos (no caso 'id' e 'description', nós podemos
    fazer da forma como está dentro das chaves
     */
    public static CategoryResponse of(Category category){
        var response = new CategoryResponse();

        // Ele basicamente irá copiar as propriedades do objeto de origem para o objeto de destino
        BeanUtils.copyProperties(category, response);
        return response;
    }
}
