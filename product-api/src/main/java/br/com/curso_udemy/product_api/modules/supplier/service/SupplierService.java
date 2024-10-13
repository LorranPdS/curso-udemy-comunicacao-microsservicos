package br.com.curso_udemy.product_api.modules.supplier.service;

import br.com.curso_udemy.product_api.config.exception.ValidationException;
import br.com.curso_udemy.product_api.modules.supplier.dto.SupplierRequest;
import br.com.curso_udemy.product_api.modules.supplier.dto.SupplierResponse;
import br.com.curso_udemy.product_api.modules.supplier.model.Supplier;
import br.com.curso_udemy.product_api.modules.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierResponse save(SupplierRequest request){
        validateCategoryNameInformed(request);
        var supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    public Supplier findById(Integer id){
        return supplierRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no supplier for the given id"));
    }

    private void validateCategoryNameInformed(SupplierRequest request){
        if(isEmpty(request.getName())){
            throw new ValidationException("The supplier's name was not informed.");
        }
    }
}