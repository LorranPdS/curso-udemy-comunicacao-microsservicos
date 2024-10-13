package br.com.curso_udemy.product_api.modules.produto.controller;

import br.com.curso_udemy.product_api.modules.produto.dto.CategoryRequest;
import br.com.curso_udemy.product_api.modules.produto.dto.CategoryResponse;
import br.com.curso_udemy.product_api.modules.produto.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request){
        return categoryService.save(request);
    }
}
