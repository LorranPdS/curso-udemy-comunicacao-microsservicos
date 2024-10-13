package br.com.curso_udemy.product_api.modules.produto.service;

import br.com.curso_udemy.product_api.config.exception.ValidationException;
import br.com.curso_udemy.product_api.modules.produto.dto.CategoryRequest;
import br.com.curso_udemy.product_api.modules.produto.dto.CategoryResponse;
import br.com.curso_udemy.product_api.modules.produto.model.Category;
import br.com.curso_udemy.product_api.modules.produto.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static org.springframework.util.ObjectUtils.isEmpty;

/*
A anotação @Service é uma anotação que estende da anotação @Component que identifica
essa nossa classe de serviço como sendo um Componente da aplicação. Então essa classe
também fará parte dos Beans que ele irá injetar para conseguir tornar essa classe
injetável para outras classes

Interessante saber que o @Component é a anotação-pai do @Controller, @Service e do @Repository
 */
@Service
public class CategoryService {

    /*
    Será através do @Autowired que faremos a nossa injeção de dependência, ou seja,
    iremos injetar essas dependências (CategoryRepository por exemplo) nessa nossa
    classe
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /*
    Então veja que:
    - iremos enviar uma request (um DTO)
    - ele vai converter em uma Category
    - depois irá devolver para nós um response
     */
    public CategoryResponse save(CategoryRequest request){
        validateCategoryNameInformed(request);
        var category = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    private void validateCategoryNameInformed(CategoryRequest request){
        if(isEmpty(request.getDescription())){
            throw new ValidationException("The category description was not informed.");
        }
    }
}
