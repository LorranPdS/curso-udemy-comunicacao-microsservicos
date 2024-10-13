package br.com.curso_udemy.product_api.modules.product.controller;

import br.com.curso_udemy.product_api.modules.product.dto.ProductRequest;
import br.com.curso_udemy.product_api.modules.product.dto.ProductResponse;
import br.com.curso_udemy.product_api.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// Esse é mais um endpoint que finalizamos, que é o endpoint de criação de product
@Controller
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request){
        return productService.save(request);
    }
}
