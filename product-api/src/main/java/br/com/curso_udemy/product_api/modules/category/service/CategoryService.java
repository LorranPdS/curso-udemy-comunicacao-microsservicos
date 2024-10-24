package br.com.curso_udemy.product_api.modules.category.service;

import br.com.curso_udemy.product_api.config.exception.SuccessResponse;
import br.com.curso_udemy.product_api.config.exception.ValidationException;
import br.com.curso_udemy.product_api.modules.category.dto.CategoryRequest;
import br.com.curso_udemy.product_api.modules.category.dto.CategoryResponse;
import br.com.curso_udemy.product_api.modules.category.model.Category;
import br.com.curso_udemy.product_api.modules.category.repository.CategoryRepository;
import br.com.curso_udemy.product_api.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ProductService productService;

    public CategoryResponse findByIdResponse(Integer id){
        return CategoryResponse.of(findById(id));
    }

    public List<CategoryResponse> findAll(){
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse> findByDescription(String description){
        if(isEmpty(description)){
            throw new ValidationException("The category description must be informed.");
        }
        return categoryRepository
                .findByDescriptionIgnoreCaseContaining(description)
                .stream()
                .map(CategoryResponse::of)
                // Acima também pode ser assim: .map(category -> CategoryResponse.of(category))
                .collect(Collectors.toList());
    }

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

    public CategoryResponse update(CategoryRequest request, Integer id){
        validateCategoryNameInformed(request);
        validateInformedId(id);
        var category = Category.of(request);
        category.setId(id);
        categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    public Category findById(Integer id){
        validateInformedId(id);
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no category for the given id"));
    }

    private void validateCategoryNameInformed(CategoryRequest request){
        if(isEmpty(request.getDescription())){
            throw new ValidationException("The category description was not informed.");
        }
    }

    public SuccessResponse delete(Integer id){
        validateInformedId(id);
        if(productService.existsByCategoryId(id)){
            throw new ValidationException("You cannot delete this category because it's already defined by a product.");
        }
        categoryRepository.deleteById(id);
        return SuccessResponse.create("The category was deleted.");
    }

    private void validateInformedId(Integer id){
        if(isEmpty(id)){
            throw new ValidationException("The category ID must be informed.");
        }
    }
}
