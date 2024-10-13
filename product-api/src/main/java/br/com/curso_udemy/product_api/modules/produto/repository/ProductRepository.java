package br.com.curso_udemy.product_api.modules.produto.repository;

import br.com.curso_udemy.product_api.modules.produto.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
