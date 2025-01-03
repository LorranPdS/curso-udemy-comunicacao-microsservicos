package br.com.curso_udemy.product_api.modules.supplier.controller;

import br.com.curso_udemy.product_api.config.exception.SuccessResponse;
import br.com.curso_udemy.product_api.modules.supplier.dto.SupplierRequest;
import br.com.curso_udemy.product_api.modules.supplier.dto.SupplierResponse;
import br.com.curso_udemy.product_api.modules.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public SupplierResponse save(@RequestBody SupplierRequest request){
        return supplierService.save(request);
    }

    @GetMapping
    public List<SupplierResponse> findAll(){
        return supplierService.findAll();
    }

    @GetMapping("{id}")
    public SupplierResponse findById(@PathVariable Integer id){
        return supplierService.findByIdResponse(id);
    }

    @GetMapping("name/{name}")
    public List<SupplierResponse> findByName(@PathVariable String name){
        return supplierService.findByName(name);
    }

    @PutMapping("{id}")
    public SupplierResponse update(@RequestBody SupplierRequest request, @PathVariable Integer id){
        return supplierService.update(request, id);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id){
        return supplierService.delete(id);
    }

}
