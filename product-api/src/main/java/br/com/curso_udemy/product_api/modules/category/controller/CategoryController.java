package br.com.curso_udemy.product_api.modules.category.controller;

import br.com.curso_udemy.product_api.config.exception.SuccessResponse;
import br.com.curso_udemy.product_api.modules.category.dto.CategoryRequest;
import br.com.curso_udemy.product_api.modules.category.dto.CategoryResponse;
import br.com.curso_udemy.product_api.modules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request){
        return categoryService.save(request);
    }

    @GetMapping
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }

    /*
            OBSERVAÇÃO ABAIXO:
                Como o 'id' do parâmetro ao lado do @PathVariable está igual ao nome 'id' do GetMapping,
                então não precisamos definir nada no @PathVariable.
                Caso você quisesse colocar um nome diferente para o id que está no parâmetro (por exemplo,
                colocar 'Integer idCategory'), daí você teria que colocar o PathVariable
                 da mesma maneira que está no @GetMapping fazendo da seguinte maneira:

                @GetMapping("{id}")
                public CategoryResponse findById(@PathVariable(name = "id") Integer idCategory){
         */
    @GetMapping("{id}")
    public CategoryResponse findById(@PathVariable Integer id){
        return categoryService.findByIdResponse(id);
    }

    /*
        OBSERVAÇÃO:
        Ao mapear o endpoint, eu não posso colocar da seguinte maneira como fiz acima:
            @GetMapping("{description}")]

        Eu não posso fazer isso porque já tenho um endpoint "get/algumacoisa" que no caso é o
        do método "findById" acima

     */
    @GetMapping("description/{description}")
    public List<CategoryResponse> findByDescription(@PathVariable String description){
        return categoryService.findByDescription(description);
    }

    @PutMapping("{id}")
    public CategoryResponse update(@RequestBody CategoryRequest request, @PathVariable Integer id){
        return categoryService.update(request, id);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id){
        return categoryService.delete(id);
    }

}
