package br.com.curso_udemy.product_api.modules.product.controller;

import br.com.curso_udemy.product_api.config.exception.SuccessResponse;
import br.com.curso_udemy.product_api.modules.product.dto.ProductCheckStockRequest;
import br.com.curso_udemy.product_api.modules.product.dto.ProductRequest;
import br.com.curso_udemy.product_api.modules.product.dto.ProductResponse;
import br.com.curso_udemy.product_api.modules.product.dto.ProductSalesResponse;
import br.com.curso_udemy.product_api.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Esse é mais um endpoint que finalizamos, que é o endpoint de criação de product
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request){
        return productService.save(request);
    }

    @GetMapping
    public List<ProductResponse> findAll(){
        return productService.findAll();
    }

    @GetMapping("{id}")
    public ProductResponse findById(@PathVariable Integer id){
        return productService.findByIdResponse(id);
    }

    @GetMapping("name/{name}")
    public List<ProductResponse> findByName(@PathVariable String name){
        return productService.findByName(name);
    }

    @GetMapping("category/{categoryId}")
    public List<ProductResponse> findByCategoryId(@PathVariable Integer categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    @GetMapping("supplier/{supplierId}")
    public List<ProductResponse> findBySupplierId(@PathVariable Integer supplierId){
        return productService.findBySupplierId(supplierId);
    }

    @PutMapping("{id}")
    public ProductResponse update(@RequestBody ProductRequest request, @PathVariable Integer id){
        return productService.update(request, id);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id){
        return productService.delete(id);
    }

    /*
        Quem irá chamar o endpoint abaixo "é o SalesAPI" e ele irá chamar toda vez que for
        fazer uma compra, antes ele precisará bater nessa aplicação informando os produtos
        que nós queremos e as quantidades, se estão todos OK ou se tem algum que não tem
        estoque
     */
    @PostMapping("check-stock")
    public SuccessResponse checkProductStock(@RequestBody ProductCheckStockRequest request){
        return productService.checkProductsStock(request);
    }

    @GetMapping("{id}/sales")
    public ProductSalesResponse findProductSales(@PathVariable Integer id){
        return productService.findProductSales(id);
    }

}
