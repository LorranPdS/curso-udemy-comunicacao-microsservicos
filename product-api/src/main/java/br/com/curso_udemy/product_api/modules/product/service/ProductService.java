package br.com.curso_udemy.product_api.modules.product.service;

import br.com.curso_udemy.product_api.config.exception.SuccessResponse;
import br.com.curso_udemy.product_api.config.exception.ValidationException;
import br.com.curso_udemy.product_api.modules.category.service.CategoryService;
import br.com.curso_udemy.product_api.modules.product.dto.*;
import br.com.curso_udemy.product_api.modules.product.model.Product;
import br.com.curso_udemy.product_api.modules.product.repository.ProductRepository;
import br.com.curso_udemy.product_api.modules.sales.client.SalesClient;
import br.com.curso_udemy.product_api.modules.sales.dto.SalesConfirmationDTO;
import br.com.curso_udemy.product_api.modules.sales.enums.SalesStatus;
import br.com.curso_udemy.product_api.modules.sales.rabbitmq.SalesConfirmationSender;
import br.com.curso_udemy.product_api.modules.supplier.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
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

    @Autowired
    private SalesConfirmationSender salesConfirmationSender;

    @Autowired
    private SalesClient salesClient;

    public List<ProductResponse> findAll(){
        return productRepository
                .findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByName(String name){
        if(isEmpty(name)){
            throw new ValidationException("The product name must be informed.");
        }

        return productRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse findByIdResponse(Integer id){
        validateInformedId(id);
        return ProductResponse.of(findByid(id));
    }

    public Product findByid(Integer id){
        validateInformedId(id);
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no product for the given ID."));
    }

    public ProductResponse save(ProductRequest request){
        validateProductDataInformed(request);
        validateCategoryAndSupplierIdInformed(request);
        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = productRepository.save(Product.of(request, supplier, category));
        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request, Integer id){
        validateProductDataInformed(request);
        validateCategoryAndSupplierIdInformed(request);
        validateInformedId(id);
        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = Product.of(request, supplier, category);
        product.setId(id);
        productRepository.save(product);
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

    public List<ProductResponse> findBySupplierId(Integer supplierId){
        if(isEmpty(supplierId)){
            throw new ValidationException("The product's supplier ID must be informed.");
        }

        return productRepository
                .findBySupplierId(supplierId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId){
        if(isEmpty(categoryId)){
            throw new ValidationException("The product's category ID must be informed.");
        }

        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public SuccessResponse delete(Integer id){
        validateInformedId(id);
        productRepository.deleteById(id);
        return SuccessResponse.create("The product was deleted.");
    }

    private void validateInformedId(Integer id) {
        if(isEmpty(id)){
            throw new ValidationException("The product ID must be informed.");
        }
    }

    public Boolean existsByCategoryId(Integer categoryId){
        return productRepository.existsByCategoryId(categoryId);
    }

    public Boolean existsBySupplierId(Integer supplierId){
        return productRepository.existsBySupplierId(supplierId);
    }

    public void updateProductStock(ProductStockDto product){
        try {
            validateStockUpdateData(product);
            updateStock(product);

        } catch (Exception ex){
            log.error("Error while trying to update stock for message with error: {}", ex.getMessage(), ex);
            var rejectedMessage = new SalesConfirmationDTO(product.getSalesId(), SalesStatus.REJECT);
            salesConfirmationSender.sendSalesConfirmationMessage(rejectedMessage);
        }
    }

    private void updateStock(ProductStockDto product){
        var productsForUpdate = new ArrayList<Product>();
        product
                .getProducts()
                .forEach(salesProduct -> {
                    var existingProduct = findByid(salesProduct.getProductId());
                    validateQuantityInStock(salesProduct, existingProduct);
                    existingProduct.updateStock(salesProduct.getQuantity());
                    productsForUpdate.add(existingProduct);
                });
        if(!isEmpty(productsForUpdate)){
            /*
                Observação importante: usar o '.saveAll(...) após listas sempre que possível
                Por esse motivo também a lógica foi alterada porque, estávamos sempre passando
                pelo 'forEach', salvando um a um e tendo 3 interações separadas com o banco de dados,
                listando 3 vezes nos logs e essa não é uma boa prática.

                Com o 'saveAll(...)', salvamos tudo que temos que salvar no banco de dados
                com apenas uma transação, e assim o hibernate trabalha com o banco de forma mais
                autônoma no save
             */
            productRepository.saveAll(productsForUpdate);
            var approvedMessage = new SalesConfirmationDTO(product.getSalesId(), SalesStatus.APPROVED);
            salesConfirmationSender.sendSalesConfirmationMessage(approvedMessage);
        }
    }

    @Transactional
    private void validateStockUpdateData(ProductStockDto product){
        if(isEmpty(product) || isEmpty(product.getSalesId())){
            throw new ValidationException("The product data and the sales ID must be informed.");
        }
        if(isEmpty(product.getProducts())){
            throw new ValidationException("The sale's products must be informed.");
        }
        product.getProducts().forEach(salesProduct -> {
            if(isEmpty(salesProduct.getQuantity()) || isEmpty(salesProduct.getProductId())){
                throw new ValidationException("The product ID and the quantity must be informed.");
            }
        });
    }

    private void validateQuantityInStock(ProductQuantityDto salesProduct, Product existingProduct){
        if(salesProduct.getQuantity() > existingProduct.getQuantityAvailable()){
            throw new ValidationException(String.format("The product %s is out of stock.", existingProduct.getId()));
        }
    }

    public ProductSalesResponse findProductSales(Integer id){
        try{
            var product = findByid(id);
            var sales = salesClient.findSalesByProductId(product.getId())
                    .orElseThrow(() -> new ValidationException("The sales was not found by this product."));
            return ProductSalesResponse.of(product, sales.getSalesIds());
        }catch (Exception ex){
            throw new ValidationException("There was an error trying to get the product's sales.");
        }
    }

    public SuccessResponse checkProductsStock(ProductCheckStockRequest request){
        if(isEmpty(request) || isEmpty(request.getProducts())){
            throw new ValidationException("The request data and products must be informed.");
        }
        request.getProducts()
                .forEach(this::validateStock);
        return SuccessResponse.create("The Stock is OK!");
    }

    private void validateStock(ProductQuantityDto productQuantity){
        if(isEmpty(productQuantity.getProductId()) || isEmpty(productQuantity.getQuantity())){
            throw new ValidationException("Product ID and quantity must be informed.");
        }
        var product = findByid(productQuantity.getProductId());
        if(productQuantity.getQuantity() > product.getQuantityAvailable()){
            throw new ValidationException(String.format("The product %s is out of stock",  product.getId()));
        }
    }
}




















