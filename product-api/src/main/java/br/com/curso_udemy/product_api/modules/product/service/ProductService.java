package br.com.curso_udemy.product_api.modules.product.service;

import br.com.curso_udemy.product_api.config.exception.ValidationException;
import br.com.curso_udemy.product_api.modules.category.service.CategoryService;
import br.com.curso_udemy.product_api.modules.product.dto.ProductRequest;
import br.com.curso_udemy.product_api.modules.product.dto.ProductResponse;
import br.com.curso_udemy.product_api.modules.product.model.Product;
import br.com.curso_udemy.product_api.modules.product.repository.ProductRepository;
import br.com.curso_udemy.product_api.modules.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

    // O padrão é que coloquemos o valor 0 como estático em vez de colocar direto no código
    private static final Integer ZERO = 0;
    @Autowired
    private ProductRepository productRepository;
    /*
        Muita gente opta por chamar um SupplierRepository por aqui para verificar se
        o conteúdo existe no banco de dados, porém o professor optou por
        deixar a responsabilidade de acesso ao banco de dados da tabela 'Supplier'
        para o serviço SupplierService criando o método próprio.
     */
    @Autowired
    private SupplierService supplierService;

    // A mesma lógica feita com o Supplier foi feita aqui com o Category
    @Autowired
    private CategoryService categoryService;

    public ProductResponse save(ProductRequest request){
        validateProductDataInformed(request);
        validateCategoryAndSupplierIdInformed(request);
        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = productRepository.save(Product.of(request, supplier, category));
        return ProductResponse.of(product);
    }

    private void validateProductDataInformed(ProductRequest request) {
        if(isEmpty(request.getName())){
            throw new ValidationException("The product's name was not informed.");
        }

        if(isEmpty(request.getQuantityAvailable())){
            throw new ValidationException("The product's quantity was not informed.");
        }

        if(request.getQuantityAvailable() <= ZERO){
            throw new ValidationException("The quantity should not be less or equal to zero.");
        }
    }

    private void validateCategoryAndSupplierIdInformed(ProductRequest request) {
        if(isEmpty(request.getCategoryId())){
            throw new ValidationException("The category id was not informed.");
        }

        if(isEmpty(request.getSupplierId())){
            throw new ValidationException("The supplier id was not informed.");
        }
    }
}
